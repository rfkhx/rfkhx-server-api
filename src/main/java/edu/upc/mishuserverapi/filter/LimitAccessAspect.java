package edu.upc.mishuserverapi.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.upc.mishuserverapi.annotation.LimitAccess;
import edu.upc.mishuserverapi.error.RequestLimitException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@Order
public class LimitAccessAspect {

    @Getter
    private Map<String, List<Long>> limitMap = new HashMap<>();

    @Pointcut("@annotation(limitAccess)")
    public void limitAccessPointCut(LimitAccess limitAccess) {
        // 限制接口调用切面类
    }

    @Around(value = "limitAccessPointCut(limitAccess)", argNames = "point,limitAccess")
    public Object doAround(ProceedingJoinPoint point, LimitAccess limitAccess) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            String className = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();
            String key = className + "." + methodName + "#" + request.getSession().getId();
            List<Long> millisecondList = limitMap.get(key);
            long now = System.currentTimeMillis();
            if (null == millisecondList) {
                List<Long> list = new ArrayList<>();
                list.add(now);
                limitMap.put(key, list);
            } else {
                List<Long> newMillisecondList = new ArrayList<>(millisecondList.size());
                millisecondList.forEach(millisecond -> {
                    // 当前访问时间 - 历史访问时间 < 限制时间
                    if (now - millisecond < limitAccess.millisecond()) newMillisecondList.add(millisecond);
                });
                // 时间段内超过访问频次上限 - 阻断
                if (newMillisecondList.size() >= limitAccess.frequency()) {
                    log.info("接口调用过于频繁 {}", key);
                    // return ResponseResult.ok(Boolean.FALSE).code(Const.ReturnCode.REQUEST_TOO_BUSY).message(Const.ReturnMessage.REQUEST_TOO_BUSY).build();
                    throw new RequestLimitException();
                }
                newMillisecondList.add(now);
                // 更新接口访问记录
                limitMap.put(key, newMillisecondList);
            }
        }
        return point.proceed();
    }
}

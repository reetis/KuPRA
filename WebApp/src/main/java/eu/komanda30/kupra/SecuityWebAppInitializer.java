package eu.komanda30.kupra;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@SuppressWarnings("UnusedDeclaration")
public class SecuityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
    protected EnumSet<DispatcherType> getSecurityDispatcherTypes() {
        return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC, DispatcherType.FORWARD);
    }
}

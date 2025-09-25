package com.midoriPol.rs;
import com.midoriPol.dao.BaseDao;
import com.midoriPol.dao.ProductDao;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/midoripol")
public class RestApplication extends Application {
    public Set<Class<?>> getClasses() {
        BaseDao.initFactory("my-persistence-unit");
        ProductDao.init();
        Set<Class<?>> classes = new HashSet<>();
        classes.add(JacksonConfiguration.class);
        classes.add(PersonResource.class);
        classes.add(CarrelloResource.class);
        classes.add(StripeResource.class);
        classes.add(BeatsResource.class);
        classes.add(HeadersFilter.class);
        return classes;
    }
}

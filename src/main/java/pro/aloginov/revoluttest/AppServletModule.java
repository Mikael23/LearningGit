package pro.aloginov.revoluttest;

import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import pro.aloginov.revoluttest.exception.AppExceptionMapper;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(UserController.class);
        bind(AccountController.class);
        bind(TransferController.class);
        bind(AppExceptionMapper.class);
        bind(UniversityController.class);
        bind(StudentController.class);
        bind(HtmlController.class);




        serve("/*").with(GuiceContainer.class);
    }

       @Provides
       @Singleton
       @Named("ex")
        private ExecutorService scheduledExecutor() {


        ExecutorService ex = Executors.newFixedThreadPool(5);;


           return ex;
        }




    }


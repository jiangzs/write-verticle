package com.jzs.vertx;

import io.vertx.core.*;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jiangzs@gmail.com on 2017/5/4.
 */
@Slf4j
public class WriteVerticle extends AbstractVerticle {

    public static final String POST_USER_URL = "/api/users";
    public static final String POST_USER_EVENT_ADDRESS = POST_USER_URL.concat("-post");

    public static void main(String[] args) {
//        // simple deploy
//        Vertx.vertx().deployVerticle(ReadVerticle.class.getName());
//        // or with options and 10 instances
//        Vertx.vertx().deployVerticle(ReadVerticle.class.getName(),
//                new DeploymentOptions().setConfig(new JsonObject().put("foo", "bar"))
//                        .setInstances(1));


        Vertx.clusteredVertx(new VertxOptions().setClustered(true), cluster -> {
            if (cluster.succeeded()) {
                final Vertx result = cluster.result();
                result.deployVerticle(WriteVerticle.class.getName(), new DeploymentOptions().setConfig(new JsonObject().put("local", true)), handle -> {
                    if (handle.succeeded()) {
                        log.info("{} deployed", WriteVerticle.class.getName());
                    } else {
                        log.error("{} deploy error {}", WriteVerticle.class.getName(), handle.cause().getMessage());
                        result.close();
                    }
                });
            }
        });

    }
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        vertx.eventBus().consumer(POST_USER_EVENT_ADDRESS, postUser());

        startFuture.complete();
        log.info("started... {}", deploymentID());
    }

    private Handler<Message<String>> postUser() {
        return handler->{
            log.info("postUser:{}",handler.body().toString());
            handler.reply("replay postUser ==>".concat(handler.body()));
        };
    }

}

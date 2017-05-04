package com.jzs.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jiangzs@gmail.com on 2017/5/4.
 */
@Slf4j
public class WriteVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        startFuture.complete();
        log.info("started... {}", deploymentID());
    }

    public static void main(String[] args) {
        // simple deploy
        Vertx.vertx().deployVerticle(WriteVerticle.class.getName());
        // or with options and 10 instances
        Vertx.vertx().deployVerticle(WriteVerticle.class.getName(),
                new DeploymentOptions().setConfig(new JsonObject().put("foo", "bar"))
                        .setInstances(5));


//        Vertx.clusteredVertx(new VertxOptions().setClustered(true), cluster -> {
//            if (cluster.succeeded()) {
//                final Vertx result = cluster.result();
//                result.deployVerticle(MyVerticle.class.getName());
//            }
//        });

    }


}

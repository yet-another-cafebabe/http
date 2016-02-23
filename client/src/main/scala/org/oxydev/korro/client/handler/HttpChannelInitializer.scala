/*
 * Copyright (C) 2015, 2016  Vladimir Konstantinov, Yuriy Gintsyak
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.oxydev.korro.client.handler

import org.oxydev.korro.api.http.{HttpRequest, HttpResponse}
import org.oxydev.korro.internal.handler.{HttpMessageCodec, LoggingChannelHandler}
import org.oxydev.korro.util.log.Logger

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpClientCodec
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory

import java.net.URL

import scala.concurrent.Promise
import scala.concurrent.duration.FiniteDuration

/**
 * TODO: Add description.
 *
 * @author Vladimir Konstantinov
 */
class HttpChannelInitializer(url: URL, req: HttpRequest, promise: Promise[HttpResponse], timeout: FiniteDuration)
  extends ChannelInitializer[SocketChannel] {

  override def initChannel(ch: SocketChannel): Unit = {

    if (url.getProtocol equalsIgnoreCase "https") {
      val sslCtx = SslContextBuilder.forClient.trustManager(InsecureTrustManagerFactory.INSTANCE).build
      ch.pipeline.addLast("ssl", sslCtx.newHandler(ch.alloc()))
    }

    ch.pipeline.addLast("http-codec", new HttpClientCodec)
    ch.pipeline.addLast("logging", new LoggingChannelHandler(Logger("korro-channel")))
    ch.pipeline.addLast("korro-codec", new HttpMessageCodec(65536L))
    ch.pipeline.addLast("http", new HttpChannelHandler(req, promise, timeout))
  }
}

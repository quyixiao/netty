/*
 * Copyright 2019 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.testsuite.transport.socket;

import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.NetUtil;
import io.netty.util.internal.PlatformDependent;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class DatagramUnicastIPv6MappedTest extends DatagramUnicastIPv6Test {

    @Override
    protected SocketAddress newSocketAddress() {
        return new InetSocketAddress(0);
    }

    @Override
    protected InetSocketAddress sendToAddress(InetSocketAddress serverAddress) {
        InetAddress addr = serverAddress.getAddress();
        if (addr.isAnyLocalAddress()) {
            return new InetSocketAddress(NetUtil.LOCALHOST4, serverAddress.getPort());
        }
        return serverAddress;
    }

    @Override
    protected boolean disconnectMightFail(DatagramChannel channel) {
        // See https://bugs.openjdk.org/browse/JDK-8285515
        if (channel instanceof NioDatagramChannel && PlatformDependent.javaVersion() < 20) {
            return true;
        }
        return super.disconnectMightFail(channel);
    }
}

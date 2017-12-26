package net;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Nikita_Markovnikov on 2/27/2017.
 */
public class DNSCache {
    private Map<String, ServiceInfo> map;

    public DNSCache() {
        this.map = new ConcurrentHashMap<>();
    }

    public void add(String name, ServiceInfo serviceInfo) {
        this.map.put(name, serviceInfo);
    }

    public Boolean contains(String name) {
        return map.containsKey(name);
    }

    public ServiceInfo get(String name) {
        return map.get(name);
    }

    public void clear() {
        map.clear();
    }

    public Collection<ServiceInfo> services() {
        return map.values();
    }

    @Override
    public String toString() {
        return map.values()
                .stream()
                .map(ServiceInfo::toString)
                .collect(Collectors.joining("\n"));
    }

    public static class ServiceInfo {
        public String name;
        public NetInfo netInfo;
        public MDNSNode.ServiceType serviceType;

        public ServiceInfo(String name, NetInfo netInfo, MDNSNode.ServiceType serviceType) {
            this.name = name;
            this.netInfo = netInfo;
            this.serviceType = serviceType;

        }

        @Override
        public String toString() {
            return String.format("Name = %s of type [%s], net info: %s", name, serviceType, netInfo);
        }
    }

    public static class ExecutorInfo extends ServiceInfo {

        public int load;

        public ExecutorInfo(String name, NetInfo netInfo, MDNSNode.ServiceType serviceType, int load) {
            super(name, netInfo, serviceType);
            this.load = load;
        }

        @Override
        public String toString() {
            return String.format("Name = %s of type [%s], net info: %s, load = %d", name, serviceType, netInfo, load);
        }
    }

    public static class NetInfo {
        public String address;
        public int port;

        public NetInfo(String address, int port) {
            this.address = address;
            this.port = port;
        }

        @Override
        public String toString() {
            return String.format("address = %s, port = %d", address, port);
        }
    }
}

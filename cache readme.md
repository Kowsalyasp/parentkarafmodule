
# CACHE MODULE

## TABLE OF CONTENTS

- [Abstract](#abstract)
- [Requirements](#requirments)
- [Cache Configuration:](#cache-configuration)

# Abstract
 A cache server is a dedicated server acting as a storage for web content, usually to have it available in a local area network. A cache server stores previously requested information from the internet locally and temporarily. Here we used redis cache server which provides redission service. Cache services implemented by local heap cache and it is extended by both soft and weak cache. In listener package all the classes implements the default interface of required cache listener. And also metioned a set of commands to manage the cache configuration.

# Requirments

**redis -** Redis is an open source , in-memory data structure store used as a cache. Redis memory storage is quite efficient, and done in a separate process. If the application runs on a platform whose memory is garbage collected (java, etc ...), it allows handling a much bigger memory cache/store. It is fast, reliable provide atomicity and consistency. Which is best suited for distributed environment.

# Cache Configuration:
In the karaf folder, create the **configuration folder** which stores and loads all the configuration files like database connectivity, cache server, and the webserver.

For Cache Connection:
The proper way to configure Redis is by providing a Redis configuration file, usually called `cache.redis` with extension `cfg`.
(eg: cache.redis.cfg)
          
 The cfg file provides a set of commands to manage the configuration. A configuration file is a properties file containing key/value pairs: **property = value**

Server caching is one of the best methods for reducing server loads. When a request is made, the server checks its temporary storage for the necessary content before processing it the request in full.
If the requested content is available in the server cache, it will be returned to the browser right away. This enables your server to handle more traffic and return your webpages faster.

The cache.redis.cfg file contains a number of directives that have a very simple format:

We have to specify cache server and its type.
> cache.server.type = single

#### Types of Server Cache:

  **1. single:** It is a redis cache server that consists of master node but there is no slave nodes.

  **2. cluster:** Redis Cluster is an active-passive cluster implementation that consists of master and slave nodes.

  **3. sentinal:** If you have a single Redis instance and that instance goes down for some reason, you are doomed because your application depends on Redis to store data, and if your data source is down, your application is out of luck. To resolve this issue, you could add slaves to replicate your master and then just read it from the slaves until your master comes up again.  

The cache server value should be in this format:`localhost:port`
In single server we use redis cache server, By default redis-cli connects to the server at the address 127.0.0.1 with port 6379.
> cache.server = 127.0.0.1:6379

If multiple cache server to be initialized then you have to set the type as cluster or sentinal. While using multiple cache server the value of the server format should be seperated by comma.
> cache.server = 127.0.0.1:6379, 127.0.0.1:6379
  
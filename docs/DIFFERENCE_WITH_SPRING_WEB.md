---
title: "Bots configuration."
permalink: /bots-configuration
---

## Difference with Spring Web

#### Event (message) triggers multiple handlers.
Unlike Spring Web, in Chatbots events (messages) 
could trigger multiple mappings (`@RequestMapping` methods) at once.
This happens because Spring Web mappings for HTTP requests could 
determine matching order, when some mapping matches better than 
another based on HTTP request method and path.
In handling messenger events, there is no such criteria, 
so we trigger all handlers, which could handle this event.



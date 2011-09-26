Description
=============
A tiny benchmark to compare some serialization libraries.

Data Set
==============
http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html

Result
==============
serialized 1,891,707 entries



Result: serialize-msgpack
------------------------------------
execution: 10
average: 16,304.5


Result: serialize-protobuf
------------------------------------
execution: 10
average: 17,079.3


Result: deserialize-msgpack
-------------------------------------
execution: 10
average: 1,424.3


Result: deserialize-protobuf
------------------------------------
execution: 10
average: 4,712.8


Result: deserialize-plain
====================================
execution: 10
average: 13,909.2



access_log.msgpack 129552913
access_log.protobuf 131960417
access_log_Jul95 205242368

access_log.msgpack.gz 20392167
access_log.protobuf.gz 23322638
ccess_log_Jul95.gz 20676672 


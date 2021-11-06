if (redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 0) then
    redis.call('SADD', KEYS[1], ARGV[1]) ;
    redis.call('expire', KEYS[1], 3600);
    return 1 ;
else
    return 0 ;
end

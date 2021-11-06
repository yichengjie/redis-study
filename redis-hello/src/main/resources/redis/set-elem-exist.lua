if (redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 0) then
    redis.call('SADD', KEYS[1], ARGV[1]) ;
    return 1 ;
end
return 0 ;
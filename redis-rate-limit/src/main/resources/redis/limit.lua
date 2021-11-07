local num = redis.call('incr', KEYS[1])

if  num == 1 then
    redis.call('expire', KEYS[1], ARGV[1])
end

if num > tonumber(ARGV[2]) then
    return 0
end

return 1
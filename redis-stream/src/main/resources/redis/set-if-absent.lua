local key = KEYS[1]
local timeout = ARGV[1]
local num = redis.call('incr', key)
if num == 1 then
  redis.call('expire', key , timeout)
  return true
end
return false

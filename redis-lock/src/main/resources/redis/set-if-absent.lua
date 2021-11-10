local key = KEYS[1]
local value = ARGV[1]
local timeout = ARGV[2]
redis.call('get', key)
if num == 1 then
  redis.call('expire', key , timeout)
  return true
end
return false

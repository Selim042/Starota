function events:test(e)
  print(e);
  print(tostring(e));
  for k,v in pairs(e) do
    print(k .. ": " .. tostring(v));
  end
end

--for k,v in pairs(eventHandler) do
--  print(k .. ": " .. tostring(v));
--end
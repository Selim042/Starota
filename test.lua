print("users");
for k,v in pairs(discord.getUsers()) do
  print(v.getName() .. "'s roles:");
  user = v;
  for k1,v1 in pairs(user.getRoles()) do
    print(" - " .. v1.getName());
  end
end

print("channels");
channel = discord.getChannelById("504089729143406595");
if channel ~= nil then
  channel.sendMessage("Hello, World!");
else
  print("failed to send to testing");
end
channels = discord.getChannelsByName("testing");
for k,v in pairs(channels) do
  print(v.getName() .." (" .. v.getCategory() .. ")" .. ", sent: " .. tostring(v.sendMessage("This is a message from " .. _G._VERSION .. "!")));
end
for k,v in pairs(discord.getChannels()) do
  cat = v.getCategory();
  if (cat == nil) then
    print(v.getName() .. ":");
  else
    print(v.getName() .. " (" .. v.getCategory() .. "):");
  end
  for k1,v1 in pairs(v.getUsersHere()) do
    print(" - " .. v1.getName());
  end
end

print("roles");
roles = discord.getRoles();
for k,v in pairs(roles) do
  print(v.getName());
end

print("options");
print("oldVal: " .. options.getValue("trade_id"));
options.setValue("trade_id", -1);
print("newVal: " .. options.getValue("trade_id"));
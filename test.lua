print("users");
for k,v in pairs(discord.getUsers()) do
  print(v.getName() .. "'s roles:");
  user = v;
  for k1,v1 in pairs(user.getRoles()) do
    print(" - " .. v1.getName());
  end
end
print("channels");
channel = discord.getChannelById(504089729143406595);
if channel ~= nil then
  channel.sendMessage("Hello, World!");
else
  print("failed to send to testing");
end
channels = discord.getChannelsByName("testing");
for k,v in pairs(channels) do
  print(v.getName() .. ", sent: " .. tostring(v.sendMessage("This is a message from Lua!")));
end
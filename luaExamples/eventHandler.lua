function events:onMessageReceivedEvent(e)
  print('Message recieved in Lua: "' .. tostring(e.message) .. '"' .. '.  Sent by "' .. e.user.getDisplayName(e.server) .. '"');
  e.channel.sendMessage('Message recieved in Lua: "' .. tostring(e.message) .. '"' .. '.  Sent by "' .. e.user.getDisplayName(e.server) .. '"');
end

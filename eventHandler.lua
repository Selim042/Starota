function events:test(e)
  print(e);
  print(tostring(e));
  for k,v in pairs(e) do
    print(k .. ": " .. tostring(v));
  end
end

function events:onMessageReceivedEvent(e)
  print('Message recieved in Lua: "' .. e.message .. '"' .. '.  Sent by "' .. e.user.getDisplayName() .. '"');
  e.channel.sendMessage('Message recieved in Lua: "' .. e.message .. '"' .. '.  Sent by "' .. e.user.getDisplayName() .. '"');
  report = e.server.getChannelById("489249345485537301"); -- reporting channel
  report.sendMessage('Message recieved in Lua: "' .. e.message .. '"' .. '.  Sent by "' .. e.user.getDisplayName() .. '"');
end

function events:onReactionAddEvent(e)
  print(e.user.getDisplayName() .. " added a reaction: " .. e.reaction);
  e.channel.sendMessage(e.user.getDisplayName() .. " added a reaction: " .. e.reaction);
end
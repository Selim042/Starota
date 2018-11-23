local args = {...};
local message = args[1];
local channel = args[2];

local profile = getProfile(message.getAuthor());
local output = "";
output = output .. "PoGO Name: " .. profile.getPoGoName() .. "\n";
output = output .. "Level: " .. profile.getLevel() .. "\n";
output = output .. "Team: " .. profile.getTeam() .. "\n";
output = output .. "Real Name: " .. profile.getRealName() .. "\n";
channel.sendMessage(output);
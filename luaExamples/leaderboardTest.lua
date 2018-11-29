local args = {...}

local boards = getAllLeaderboards()
local output = ""
for _,v in pairs(boards) do
  output = output .. v.getDisplayName() .. ":\n"
  for r,e in pairs(v.getEntries()) do
    output = output .. " " .. r .. ") " .. tostring(e.user) .. ": " .. e.value .. "\n"
  end
end

args[2].sendMessage(output)
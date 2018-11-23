local args = {...};

for k,v in pairs(args) do
  print(k .. ": " .. tostring(v) .. " (" .. type(v) .. ")");
end

local output = "\n";

local pokeId = tonumber(args[4]) or 26;
local pokemon = getPokemon(pokeId);
print(pokemon.getName());
local forms = pokemon.getForms();
output = output .. "Pokemon #" .. pokeId .. ":\n" .. pokemon.getName() .. "\n";
if (forms ~= nil) then
  for k,v in pairs(forms) do
    output = output .. " - " .. tostring(v) .. "\n";
  end
else
  output = output .. " - No forms" .. "\n";
end

local postId = tonumber(args[5]) or 50;
local post = getTrade(postId);
output = output .. "\nTrade #" .. postId .. ":\n";
output = output .. " - Pokemon: " .. post.getPokemon().getName() .. "\n";
output = output .. " - Owner: " .. tostring(post.getOwner()) .. "\n";

--print(output);
args[2].sendMessage(output);
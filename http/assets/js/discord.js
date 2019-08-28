/*document.getElementById("sidebarToggle").onclick
    = function() {
        var cookies = getCookies();
        if ('sidebar' in cookies) {
            document.cookie = "sidebar="
                + !cookies['sidebar'];
        }
        else
            document.cookie = "sidebar=false";
};*/

function onPokemonSelect(value) {
    window.location.href = "?pokemon=" + value;
}

function onPokemonFormSelect(value) {
    insertParam("form", value);
}

function getCookies() {
    return document.cookie.split('; ').reduce((prev, current) => {
        const [name, value] = current.split('=');
        prev[name] = value;
        return prev;
    }, {});
}

function getServerIconFromID(guilds, id) {
    var ret = null;
    guilds.forEach(function(g) {
        if (ret == null && g.id === id) {
            ret = getServerIcon(g);
        }
    });
    return ret;
}

function getServerIcon(server) {
    var id = server['id'];
    var hash = server['icon'];
    if (hash == null) {
        return "/assets/img/default_server.png";
    }
    if (hash.substring(0,2) == "a_") {
        return "https://cdn.discordapp.com/icons/"
            + id + "/" + hash + ".gif";
    }
    return "https://cdn.discordapp.com/icons/"
        + id + "/" + hash + ".png";
}

function getUserAvatar(server) {
    var id = server['id'];
    var hash = server['avatar'];
    if (hash == null) {
        return "https://cdn.discordapp.com/embed/avatars/"
            + (parseInt(profile['discriminator']) % 5)
            + ".png";
    }
    if (hash.substring(0,2) == "a_") {
        return "https://cdn.discordapp.com/avatars/"
            + id + "/" + hash + ".gif";
    }
    return "https://cdn.discordapp.com/avatars/"
        + id + "/" + hash + ".png";
}

function getGuilds(token, profile) {
    var ret = [];
    $.ajax({
        type: "GET",
        url: "/guilds",
        async: false,
        success: function(data, status) {
            ret = JSON.parse(data);
        }
    });
    return ret;
}

function addGuild(guild) {
    var serverFormat =
        `<div class="dropdown-list-image mr-3">
                <img class="rounded-circle" style="width: 32px;height: 32px;" src="{SERVER_ICON}" />
            </div>
            <div class="font-weight-bold">
                <div class="text-truncate">{SERVER_NAME}</div>
            </div>`;
    var list = document.getElementById("server_list");
    var a = document.createElement("a");
    a.onclick = function() {
        document.cookie = "current_server = " + guild["id"] + "; path=/;";
        location.reload();
        return false;
    };
    a.className = "d-flex align-items-center dropdown-item";
    a.href = "#";
    a.innerHTML = serverFormat.replace("{SERVER_ICON}", 
        getServerIcon(guild)).replace("{SERVER_NAME}", guild["name"]);
    list.appendChild(a);
    
    var list404 = document.getElementById("404_server_select");
    if (list404 != null) {
        var a404 = document.createElement("a");
        a404.onclick = function() {
            document.cookie = "current_server = " + guild["id"] + "; path=/;";
            location.reload();
            return false;
        };
        a404.className = "d-flex align-items-center dropdown-item";
        a404.href = "#";
        a404.innerHTML = serverFormat.replace("{SERVER_ICON}", 
            getServerIcon(guild)).replace("{SERVER_NAME}", guild["name"]);
        list404.appendChild(a404);
    }
}

function addNoGuildsToSelector() {
    var list = document.getElementById("server_list");
    var a = document.createElement("a");
    a.className = "text-center dropdown-item small text-gray-500";
    a.href = "#";
    a.innerHTML = "No Servers";
    list.appendChild(a);
}

function insertParam(key, value) {
    key = encodeURI(key); value = encodeURI(value);

    var kvp = document.location.search.substr(1).split('&');

    var i=kvp.length; var x; while(i--) 
    {
        x = kvp[i].split('=');

        if (x[0]==key)
        {
            x[1] = value;
            kvp[i] = x.join('=');
            break;
        }
    }

    if(i<0) {kvp[kvp.length] = [key,value].join('=');}

    //this will reload the page, it's likely better to store this until finished
    document.location.search = kvp.join('&'); 
}

function removeParam(key, sourceURL) {
    var rtn = sourceURL.split("?")[0],
        param,
        params_arr = [],
        queryString = (sourceURL.indexOf("?") !== -1) ? sourceURL.split("?")[1] : "";
    if (queryString !== "") {
        params_arr = queryString.split("&");
        for (var i = params_arr.length - 1; i >= 0; i -= 1) {
            param = params_arr[i].split("=")[0];
            if (param === key) {
                params_arr.splice(i, 1);
            }
        }
        rtn = rtn + "?" + params_arr.join("&");
    }
    return rtn;
}

$( document ).ready(function() {
    var $_GET = {};

    if (document.location.toString()
            .indexOf('?') !== -1) {
        var query = document.location.toString()
            .replace(/^.*?\?/, '')
            .replace(/#.*$/, '').split('&');

        for (var i  =0, l = query.length;
                i < l; i++) {
            var aux = decodeURIComponent(query[i])
                .split('=');
            $_GET[aux[0]] = aux[1];
        }
    }
    /*if ('server' in $_GET) {
        document.cookie =
            "current_server=" + $_GET['server'];
        window.location.href
            = removeParam("server", window.location.href);
    }*/
    
    var cookies = getCookies();
    var token = cookies['token'];
    var profile = null;
    if ('token' in cookies) {
        $.ajax({
            type: "GET",
            url: "https://discordapp.com/api/v6/users/@me",
            async: false,
            headers: {
                'Authorization': "Bearer " + token,
                'Content-Type': "application/json",
            },
            success: function(data, status) {
               profile = data;
            }
        });
    } else {
        var login = document.getElementById("login");
        login.onclick = function() {
            window.location.href = "/login";
        };
        return;
    }
    document.getElementById
        ("logout_dropdown_option").onclick
        = function() {
        document.cookie =
            "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        location.reload();
    };
    if (profile == null) {
        document.getElementById("profile_name")
            .textContent = "Login";
    } else {
        document.getElementById("profile_name")
            .textContent = profile.username;
        var avatar = document
            .getElementById("profile_avatar");
        avatar.classList.remove("invisible");
        avatar.src = getUserAvatar(profile);
        document.getElementById("server_select")
            .classList.remove("invisible");

        var guilds = getGuilds(token, profile);
        if (guilds.length > 0) {
            guilds.forEach(function (g) {
                addGuild(g);
            });
        } else {
            addNoGuildsToSelector();
        }
        document.getElementById("server_count")
            .textContent = guilds.length;

        document.getElementById("current_server")
            .src = getServerIconFromID(guilds,
            cookies["current_server"]);
    }
});
var getLocation = function(href) {
  var l = document.createElement("a");
  l.href = href;
  return l;
};

var navbar = document.getElementById("accordionSidebar");
if (navbar != null) {
  var children = Array.prototype.slice.call(navbar.childNodes);
  children.forEach(function(child) {
    if (child.firstChild == null)
      return;
    if (document.location.pathname
        === getLocation(child.firstChild.href).pathname)
      child.firstChild.classList.add("active");
  });
}
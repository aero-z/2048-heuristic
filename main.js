//Main().main();

var x = 1;
$(".cell_content").each(function(i, obj) {
  obj.innerHTML = x;
  x++;
});

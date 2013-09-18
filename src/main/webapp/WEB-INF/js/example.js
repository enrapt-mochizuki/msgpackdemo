$(document).ready(function(){
  msgpack.download("/msgpackdemo", {}, function(data) {
    var text = "Customer: name " + data.name + ", age = " + data.age;
    $("#result").text(text);
  });
});
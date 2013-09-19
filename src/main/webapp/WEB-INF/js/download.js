$(document).ready(function(){
  msgpack.download("/api/download", {}, function(data) {
    var text = "Customer: name " + data.name + ", age = " + data.age;
    $("#result").text(text);
  });
});
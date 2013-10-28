$(document).ready(function() {
  $.ajaxPrefilter(function(options) {
    if (options["prefilter"] == "msgpack") {
      var toString = true;
      var json = options.data;
      var packed = msgpack.pack(json, toString);
      var encoded = $.base64.encode(packed);
      options.data = encoded;
    }
  });

  $.ajaxSetup({
    "converters": {
      "* base64": function(encoded) {
        var data = $.base64.decode(encoded);
        return data;
      },
      "* msgpack": function(packed) {
        var unpacked = msgpack.unpack(packed);
        return unpacked;
      }
    }
  });

  var customers = {
    "Sherlock Shellingford": {
      "name": "Sherlock Shellingford",
      "age": 15
    },
    "Cordelia Glauca": {
      "name": "Cordelia Glauca",
      "age": 17
    }
  };

  $("#submit").click(function() {
    var data = $("#text").val();
    var customer_name = $("#customer_name").val();
    var customer = customers[customer_name];

    var request_mime_type = $("#request_mime_type").val();

    var content_type;
    var data;

    if (request_mime_type == "json") {
      content_type = "application/json";
      data = JSON.stringify(customer);
    } else if (request_mime_type == "msgpack") {
      content_type = "application/x-msgpack";
      data = customer;
    }

    var response_mime_type = $("#response_mime_type").val();

    var url;
    var data_type;

    if (response_mime_type == "json") {
      url = "/api/both.json";
      data_type = "json";
    } else if (response_mime_type == "msgpack") {
      url = "/api/both.msgpack";
      data_type = "base64 msgpack";
    }

    var settings = {
      "type": "POST",
      "url": url,
      "data": data,
      "contentType": content_type,
      "processData": false,
      "success": function(json) {
        $("#result").text(JSON.stringify(json));
      },
      "dataType": data_type
    };

    if (request_mime_type == "msgpack") {
      settings["prefilter"] = "msgpack";
    }

    $.ajax(settings);
  });
});
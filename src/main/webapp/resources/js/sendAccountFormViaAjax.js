$(document).ready(function () {
    $('#accountAdding').find('form').validator().on('submit', function (event) {
        if (!event.isDefaultPrevented()) {
            event.preventDefault();
            var form = $(this);
            var data = form.serializeArray();
            var objectifyData = objectifyForm(data);
            var jsonData = JSON.stringify(objectifyData);
            $.ajax({
                type: 'POST',
                beforeSend: function (request) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    request.setRequestHeader(header, token);
                },
                contentType: 'application/json; charset=UTF-8',
                url: form.attr('action'),
                data: jsonData
            }).done(function (data) {
                form.find(".with-errors").each(function () {
                    if ($(this).hasClass("fail")) {
                        $(this).removeClass("fail");
                    }
                });
                form.each(function () {
                    this.reset();
                });
                $('#accountAdd').modal('hide');
                location.reload();
            }).fail(function (error) {
                form.find(".with-errors").each(function () {
                    $(this).addClass("fail");
                    $(this).text("Введите корректные данные");
                });
            });
        } else {
            event.preventDefault();
        }
    });
    function objectifyForm(formArray) {  //serialize data function
        var returnArray = {};
        for (var i = 0; i < formArray.length; i++) {
            returnArray[formArray[i]['name']] = formArray[i]['value'];
        }
        return returnArray;
    }
});

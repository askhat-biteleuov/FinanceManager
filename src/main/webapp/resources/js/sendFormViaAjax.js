$(document).ready(function () {
    $('#adding').find('form').validator().on('submit', function (event) {
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
                form.hide('slow');
                form.each(function () {
                    this.reset();
                });
            }).fail(function (error) {
                alert('FAIL ' + error);
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

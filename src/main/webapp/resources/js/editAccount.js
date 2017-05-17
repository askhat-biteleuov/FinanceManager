$(document).ready(function () {

    $('.editBtn').click(function (e) {
        e.preventDefault();
        $(this).closest('.editDiv').find(".acceptBtn").show();
        $(this).closest('.editDiv').find(".cancelBtn").show();
        $(this).hide();
        var field = $(this).closest('.editDiv').find(".editField");
        field.removeClass('ellipsis');
        var oldVal = $(this).closest('.editDiv').find(".oldVal");
        oldVal.val(field.text());
        field.attr('contenteditable', 'true');
        field.focus();
    });

    $('.acceptBtn').click(function (e) {
        e.preventDefault();
        var field = $(this).closest('.editDiv').find(".editField");
        var oldVal = $(this).closest('.editDiv').find(".oldVal");
        var newVal = field.text();
        if(newVal !== oldVal.val()){
            var accountId = $(this).closest('.editDiv').find(".accountId").val();
            var accountBalance = $(this).closest('.editDiv').find(".accountBalance").val();
            $.ajax({
                type: 'POST',
                beforeSend: function (request) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    request.setRequestHeader(header, token);
                },
                contentType: 'application/json; charset=UTF-8',
                url: "/account/edit",
                data:  JSON.stringify({ "id": accountId, "name": newVal.trim(), "balance": accountBalance})
            }).done(function (data) {
                stopEdit(field);
            }).fail(function (error) {
                var errors = error.responseJSON;
                var template = '<div class="popover alert-danger" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>';
                field.closest('.editDiv').find(".acceptBtn").popover({placement:"bottom",template:template,trigger:"manual"});
                field.closest('.editDiv').find(".acceptBtn").data('bs.popover').options.content =  errors["name"];
                field.closest('.editDiv').find(".acceptBtn").popover('show');
            });
        }
    });

    $('.cancelBtn').click(function (e) {
        e.preventDefault();
        var field = $(this).closest('.editDiv').find(".editField");
        var oldVal = $(this).closest('.editDiv').find(".oldVal");
        field.text(oldVal.val());
        stopEdit(field);
    });

    $('.editField').on('keyup', function (event) {
        var esc = event.which === 27;
        var element = event.target;
        if (esc) {
            stopEdit(element);
            var oldVal = $(this).closest('.editDiv').find(".oldVal");
            element.text(oldVal.val());
        }
    }).click(function (e) {
        var isContenteditable = $(this).attr("contenteditable");
        if(isContenteditable==="true") {
            e.preventDefault();
        }
    });


    function stopEdit(element) {
        element.addClass('ellipsis');
        element.closest('.editDiv').find(".acceptBtn").popover('destroy');
        element.attr('contenteditable', 'false');
        element.closest('.editDiv').find(".acceptBtn").hide();
        element.closest('.editDiv').find(".cancelBtn").hide();
        element.closest('.editDiv').find(".editBtn").show();
    }
});
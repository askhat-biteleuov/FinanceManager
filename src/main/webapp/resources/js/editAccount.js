$(document).ready(function () {

    $('.editAccountBtn').click(function (e) {
        e.preventDefault();
        $(this).hide();
        var editDiv = $(this).closest('.editAccountDiv');
        editDiv.find(".acceptAccountBtn").show();
        editDiv.find(".cancelAccountBtn").show();
        var field = editDiv.find(".editAccountName");
        var oldVal = editDiv.find(".oldAccountName");
        oldVal.val(field.text());
        field.attr('contenteditable', 'true');
        field.focus();
    });

    $('.acceptAccountBtn').click(function (e) {
        e.preventDefault();
        var editDiv = $(this).closest('.editAccountDiv');
        var field = editDiv.find(".editAccountName");
        var oldVal = $.trim(editDiv.find(".oldAccountName").val());
        var newVal = field.text().trim();
        if (newVal !== oldVal) {
            var accountId = editDiv.find(".accountId").val();
            var accountBalance = editDiv.find(".accountBalance").val();
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
                stopEdit(editDiv);
            }).fail(function (error) {
                var errors = error.responseJSON;
                var template = '<div class="popover alert-danger" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>';
                editDiv.find(".acceptAccountBtn").popover({placement: "bottom", template: template, trigger: "manual"});
                editDiv.find(".acceptAccountBtn").data('bs.popover').options.content = errors["name"];
                editDiv.find(".acceptAccountBtn").popover('show');
            });
        } else {
            stopEdit(editDiv);
        }
    });

    $('.cancelAccountBtn').click(function (e) {
        e.preventDefault();
        var editDiv = $(this).closest('.editAccountDiv');
        var field = editDiv.find(".editAccountName");
        var oldVal = editDiv.find(".oldAccountName");
        field.text(oldVal.val());
        stopEdit(editDiv);
    });

    $('.editAccountName').on('keyup keydown', function (event) {
        var editDiv = $(this).closest('.editAccountDiv');
        var esc = event.which === 27;
        if (esc) {
            stopEdit(editDiv);
            var oldVal = editDiv.find(".oldAccountName");
            $(this).text(oldVal.val());
        }
        var enter = event.which === 13;
        if (enter) {
            event.preventDefault();
        }
    }).click(function (e) {
        var isContenteditable = $(this).attr("contenteditable");
        if(isContenteditable==="true") {
            e.preventDefault();
        }
    });

    function stopEdit(editDiv) {
        editDiv.find(".acceptAccountBtn").popover('destroy');
        editDiv.find(".editAccountName").attr('contenteditable', 'false');
        editDiv.find(".acceptAccountBtn").hide();
        editDiv.find(".cancelAccountBtn").hide();
        editDiv.find(".editAccountBtn").show();
    }
});
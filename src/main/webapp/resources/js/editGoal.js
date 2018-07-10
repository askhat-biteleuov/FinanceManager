$(document).ready(function () {

    $('.editGoalBtn').click(function (e) {
        e.preventDefault();
        var editGoalDiv = $(this).closest('.editGoalDiv');
        editGoalDiv.find(".acceptGoalBtn").show();
        editGoalDiv.find(".cancelGoalBtn").show();
        $(this).hide();
        var goalName = editGoalDiv.find(".editGoalName");
        var oldGoalName = editGoalDiv.find(".oldGoalName");
        oldGoalName.val(goalName.text());
        goalName.attr('contenteditable', 'true');
        goalName.focus();

        editGoalDiv.find(".defaultGoalAmount").hide();
        var goalAmount = editGoalDiv.find(".editGoalAmount");
        goalAmount.show();
        goalAmount.attr('contenteditable', 'true');
        var oldGoalAmount = editGoalDiv.find(".oldGoalAmount");
        oldGoalAmount.val(goalAmount.text());

        editGoalDiv.find(".defaultGoalDate").hide();
        var goalDate = editGoalDiv.find(".editGoalDate");
        editGoalDiv.find('[name=date]').val(editGoalDiv.find(".defaultGoalDate").text().trim());
        goalDate.show();
    });

    $('.acceptGoalBtn').click(function (e) {
        e.preventDefault();
        var editGoalDiv = $(this).closest('.editGoalDiv');

        var goalName = editGoalDiv.find(".editGoalName");
        var oldGoalName = $.trim(editGoalDiv.find(".oldGoalName").val());
        var newGoalName = goalName.text().trim();

        var goalAmount = editGoalDiv.find(".editGoalAmount");
        var oldGoalAmount = $.trim(editGoalDiv.find(".oldGoalAmount").val());
        var newGoalAmount = goalAmount.text().trim();

        var newGoalDate = editGoalDiv.find('[name=date]').val();
        var oldGoalDate = editGoalDiv.find(".defaultGoalDate").text().trim();

        if (newGoalName !== oldGoalName || newGoalAmount !== oldGoalAmount || newGoalDate !== oldGoalDate) {
            var goalId = $(this).closest('.editGoalDiv').find(".goalId").val();
            $.ajax({
                type: 'POST',
                beforeSend: function (request) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    request.setRequestHeader(header, token);
                },
                contentType: 'application/json; charset=UTF-8',
                url: "/goal/edit",
                data: JSON.stringify({
                    "id": goalId,
                    "name": newGoalName,
                    "goalAmount": newGoalAmount,
                    "date": newGoalDate
                })
            }).done(function (data) {
                location.reload();
            }).fail(function (error) {
                var errors = error.responseJSON;
                var template = '<div class="popover alert-danger" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>';
                editGoalDiv.find(".acceptGoalBtn").popover({
                    placement: "bottom",
                    template: template,
                    trigger: "manual"
                });
                if (errors !== undefined) {
                    if (errors["name"] !== undefined && errors["goalAmount"] !== undefined && errors["date"] !== undefined) {
                        editGoalDiv.find(".acceptGoalBtn").data('bs.popover').options.content = errors["name"] + "; " + errors["goalAmount"] + "; " + errors["date"];
                    }
                    if (errors["name"] !== undefined && errors["goalAmount"] !== undefined) {
                        editGoalDiv.find(".acceptGoalBtn").data('bs.popover').options.content = errors["name"] + "; " + errors["goalAmount"];
                    }
                    if (errors["name"] !== undefined && errors["date"] !== undefined) {
                        editGoalDiv.find(".acceptGoalBtn").data('bs.popover').options.content = errors["name"] + "; " + errors["date"];
                    }
                    if (errors["goalAmount"] !== undefined && errors["date"] !== undefined) {
                        editGoalDiv.find(".acceptGoalBtn").data('bs.popover').options.content = errors["goalAmount"] + "; " + errors["date"];
                    }
                    if (errors["name"] !== undefined) {
                        editGoalDiv.find(".acceptGoalBtn").data('bs.popover').options.content = errors["name"];
                    }
                    if (errors["goalAmount"] !== undefined) {
                        editGoalDiv.find(".acceptGoalBtn").data('bs.popover').options.content = errors["goalAmount"];
                    }
                    if (errors["date"] !== undefined) {
                        editGoalDiv.find(".acceptGoalBtn").data('bs.popover').options.content = errors["date"];
                    }
                }
                editGoalDiv.find(".acceptGoalBtn").popover('show');
            });
        } else {
            editGoalDiv.find('.cancelGoalBtn').click();
        }
    });

    $('.cancelGoalBtn').click(function (e) {
        e.preventDefault();
        var editDiv = $(this).closest('.editGoalDiv');
        var goalName = editDiv.find(".editGoalName");
        var oldGoalName = editDiv.find(".oldGoalName");
        goalName.text(oldGoalName.val());
        var goalLimit = editDiv.find(".editGoalAmount");
        var oldGoalLimit = editDiv.find(".oldGoalAmount");
        goalLimit.text(oldGoalLimit.val());
        stopEdit(editDiv);
    });

    $('.editGoalName').on('keyup keydown', function (event) {
        var editGoalDiv = $(this).closest('.editGoalDiv');
        var esc = event.which === 27;
        if (esc) {
            editGoalDiv.find('.cancelGoalBtn').click();
        }
        var enter = event.which === 13;
        if (enter) {
            event.preventDefault();
        }
    }).click(function (e) {
        var isContenteditable = $(this).attr("contenteditable");
        if (isContenteditable === "true") {
            e.preventDefault();
        }
    });

    $('.editGoalAmount').on('keyup keydown', function (event) {
        var editGoalDiv = $(this).closest('.editGoalDiv');
        var esc = event.which === 27;
        if (esc) {
            editGoalDiv.find('.cancelGoalBtn').click();
        }
        var enter = event.which === 13;
        if (enter) {
            event.preventDefault();
        }
    }).click(function (e) {
        var isContenteditable = $(this).attr("contenteditable");
        if (isContenteditable === "true") {
            e.preventDefault();
        }
    });

    $('.glyphicon-calendar, #overdueGoalDate').on('click', function (event) {
        event.preventDefault();
    });


    function stopEdit(element) {
        element.find(".acceptGoalBtn").popover('destroy');
        element.find(".editGoalName").attr('contenteditable', 'false');
        element.find(".editGoalAmount").attr('contenteditable', 'false');
        element.find(".acceptGoalBtn").hide();
        element.find(".cancelGoalBtn").hide();
        element.find(".editGoalAmount").hide();
        element.find(".editGoalBtn").show();
        element.find(".defaultGoalAmount").show();
        element.find(".editGoalDate").hide();
        element.find(".defaultGoalDate").show();
    }
});

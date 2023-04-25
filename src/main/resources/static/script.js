$(document).ready(function () {
    $('.edit-button').on('click', function(event) {
        event.preventDefault();
        $('#user-profile').modal("show").find('.modal-dialog').load($(this).attr('href'), function(response, status, xhr) {
            if (xhr.status === 404) {
                $(location).attr('href', '/admin');
            }
            $('#user-profile .modal-header h3').text('Edit User');
            let submit = $('#user-profile .modal-footer .submit');
            submit.text('Save');
            submit.addClass('btn-primary');
            $('#user-profile #method').val("patch");
        });
    });

    $('.delete-button').on('click', function(event) {
        event.preventDefault();
        $('#user-profile').modal("show").find('.modal-dialog').load($(this).attr('href'), function(response, status, xhr) {
            if (xhr.status === 404) {
                $(location).attr('href', '/admin');
            }
            $('#user-profile .modal-header h3').text('Delete User');
            $("#user-profile #firstName").prop("readonly", true);
            $("#user-profile #lastName").prop("readonly", true);
            $("#user-profile #age").prop("readonly", true);
            $("#user-profile #email").prop("readonly", true);
            $("#user-profile #roles").prop("disabled", true);
            $('#user-profile #password-div').remove();
            let submit_btn = $('#user-profile .modal-footer .submit');
            submit_btn.text('Delete');
            submit_btn.addClass('btn-danger');
            $('#user-profile #method').val("delete");
        });
    });
});
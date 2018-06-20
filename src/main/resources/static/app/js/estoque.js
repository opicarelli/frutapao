Utils = {};
Utils.Datatables = {};

Utils.Datatables.i18n = {
    "sEmptyTable": "Nenhum registro encontrado",
    "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
    "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
    "sInfoFiltered": "(Filtrados de _MAX_ registros)",
    "sInfoPostFix": "",
    "sInfoThousands": ".",
    "sLengthMenu": "_MENU_ resultados por página",
    "sLoadingRecords": "Carregando...",
    "sProcessing": "Processando...",
    "sZeroRecords": "Nenhum registro encontrado",
    "sSearch": "Pesquisar",
    "oPaginate": {
        "sNext": "Próximo",
        "sPrevious": "Anterior",
        "sFirst": "Primeiro",
        "sLast": "Último"
    },
    "oAria": {
        "sSortAscending": ": Ordenar colunas de forma ascendente",
        "sSortDescending": ": Ordenar colunas de forma descendente"
    },
    "decimal": ","
};

EstoqueCtrl = new function () {

    that = this;

    this.datatableEstoque = null;

    this.init = function () {
        this.defineFields();
        this.takeControls();

        this.loadEstoque();
    };

    this.defineFields = function() {
        this.defineDatatableEstoque();
    };

    this.defineDatatableEstoque = function() {
        this.datatableEstoque = $("#datatable-estoque").DataTable({
            scrollX: true,
            //bFilter: false, // Ocultar o campo pesquisar
            order: [[ 0, "asc" ]], // Ordenacao nome
            language: Utils.Datatables.i18n, // Internacionalizacao pt_BR
            columns: [
                { "data": "nome" },
                { "data": "unidade-medida" },
                { "data": "preco-medio" }
            ]
        });
    };

    this.takeControls = function() {
    };

    this.loadEstoque = function() {
        $.ajax({
            url: "/estoque/search",
            type: "GET",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                that.datatableEstoque.clear();
                $.each(data, function(idx, obj) {
                    that.datatableEstoque.row.add( {
                        "nome" : obj.nome,
                        "unidade-medida" : obj.unidadeMedida,
                        "preco-medio": obj.precoMedio
                    });
                });
                that.datatableEstoque.draw();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("jqXHR", jqXHR);
                console.error("textStatus", textStatus);
                console.error("errorThrown", errorThrown);
            }
        });
    };

}
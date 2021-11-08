$(function(){
    console.log("El jquery en login funcionando");
    
    
    $(document).on("submit","#formulario_login",function(e){
        
        e.preventDefault();
        var datos = $("#formulario_login").serialize();
        $.ajax({
            dataType: "json",
            method: "POST",
            url:'Login',
            data : datos,
        }).done(function(json) {
        	console.log("EL TRAER al logueo",json);
                
        }).fail(function(){

        }).always(function(){
            Swal.close();
        });
        
    });
})
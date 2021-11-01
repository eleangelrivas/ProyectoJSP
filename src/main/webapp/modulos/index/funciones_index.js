$(function(){//funcion anonima
    $('#formulario_registro').parsley();
    $.mask.definitions['~']='[2,6,7]';
    $('#telefono').mask("~999-9999"); 
    
    
    console.log("Jquery funcionando podemos ver"); 
    $("#cargar_tabla").empty().append("<p>Hola como estas</p>");
    cargar_datos();
    
    $(document).on("click","#boton_editar",function(e){
        e.preventDefault();
        mostrar_cargando("Consultando información","Por favor no recargue la página");
        var datos = {"consultar_datos":"traer_especifico",employee_id:$(this).attr('data-id')};
        $.ajax({
            dataType: "json",
            method: "POST",
            url:'Inicio',
            data : datos,
        }).done(function(json) {
        	console.log("EL TRAER ESPECIFICO",json);
                if(json[0]['employee_id']!=""){
                    $("#nombre").val(json[0]['first_name']);
                    $("#apellido").val(json[0]['last_name']);
                    $("#email").val(json[0]['email']);
                    $("#telefono").val(json[0]['phone_number']);
                    $("#salario").val(json[0]['salary']);
                    $("#fecha1").val(json[0]['hire_date']);
                    $("#consultar_datos").val("si_actualizalo");
                    $("#llave_persona").val(json[0]['employee_id']);
                    
                    $("#md_registrar_usuario").modal("show"); 
                }
        }).fail(function(){

        }).always(function(){
            Swal.close();
        });
    });
    
    $(document).on("click",".btn_cerrar_class",function(e){
        $("#md_registrar_usuario").modal("hide");
    });
    $(document).on("click","#mostrar_modal",function(e){
        e.preventDefault();
        if($(this).attr('data-tipo')=="registro"){
            
        }else{
            
        }
        
        $("#md_registrar_usuario").modal("show");
    });
    
    
    $(document).on("submit","#formulario_registro",function(e){
        e.preventDefault();
        var datos = $("#formulario_registro").serialize();
        console.log("Imprimiendo datos: ",datos);
        mostrar_cargando("Almacenando información","Por favor no recargue la página");
        $.ajax({
            dataType: "json",
            method: "POST",
            url:'Inicio',
            data : datos,
        }).done(function(json) {
        	console.log("EL GUARDAR",json);
        	$('#md_registrar_usuario').modal('hide');
        	if (json[0]['resultado']=="insertado") {
                    Swal.fire(
                        'Excelente',
                        'Datos registrados',
                        'success'
                    )
                    cargar_datos();
        	}else{ 
                    cargar_datos();
        	}
                $("#formulario_registro").trigger('reset');
        	
        }).fail(function(){

        }).always(function(){
            Swal.close();
        });


    });
    
    
    $(document).on("click","#boton_eliminar",function(e){
        e.preventDefault();
        var id = $(this).attr("data-id");
        Swal.fire({
            title: '¿Desea eliminar el elemento?',
            html:'Esta acción no puede ser revertida',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Si, eliinar',
            denyButtonText: 'No eliminar',
          }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
              eliminar_dato($(this).attr("data-id"));
            } else if (result.isDenied) {
              Swal.fire('Acción cancelada por el usuairo', '', 'info')
            }
          })


    });


});
    
    function eliminar_dato(el_id){
        mostrar_cargando("Procesando solicitud","Espere mientras se elimina la información")
	var datos = {"consultar_datos":"si_eliminalo",id:el_id};
	$.ajax({
            dataType:"json",
            method:"POST",
            url:"Inicio",
            data:datos
	}).done(function(json){
            
            console.log("datos elimninados ",json);
            cargar_datos();
	}).fail(function(){

	}).always(function(){
            Swal.close();
	})
    }
    function cargar_datos(){
	mostrar_cargando("Procesando solicitud","Espere mientras se obtiene la información")
	var datos = {"consultar_datos":"si_consultalos"};
	$.ajax({
            dataType:"json",
            method:"POST",
            url:"Inicio",
            data:datos
	}).done(function(json){
            Swal.close();
            console.log("datos consultados ",json);
            //if (json[0]=="Exito") {
            $("#cargar_tabla").empty().html(json[0]['la_tabla_html']);
            $('#tabla_empleados').DataTable({
                responsive: true,
                pageLength:10,
                lengthMenu:[[10,20,30,-1],[10,20,30,'Todo']],
                "language":{
                    "sSearch":"Buscar:",
                    "oPaginate":{
                        "sFirst":"Primero",
                        "sLast":"Último",
                        "sNext":"Siguiente",
                        "sPrevious":"Anterior"
                    }
                }
            });
            //}
	}).fail(function(){

	}).always(function(){

	})

    }
    
    function mostrar_cargando(titulo,mensaje=""){
	Swal.fire({
	  title: titulo,
	  html: mensaje,
	  timer: 2000,
	  timerProgressBar: true,
	  didOpen: () => {
	    Swal.showLoading()
	     
	  },
	  willClose: () => {
	     
	  }
	}).then((result) => {
	  /* Read more about handling dismissals below */
	  if (result.dismiss === Swal.DismissReason.timer) {
	    console.log('I was closed by the timer')
	  }
	})
    }
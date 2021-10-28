<%-- 
    Document   : index
    Created on : 10-25-2021, 12:04:18 PM
    Author     : rivas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="inc/template_start.jsp" %>
<%@ include file="inc/page_head.jsp" %> 

<!-- Page content -->
<div id="page-content">
    <!-- Dashboard Header -->
    <!-- For an image header add the class 'content-header-media' and an image as in the following example -->
     
    
    <!-- Mini Top Stats Row -->
    <div class="row">
        <div class="col-sm-6 col-lg-3">
            <!-- Widget -->
            <a href="page_ready_article.php" class="widget widget-hover-effect1">
                <div class="widget-simple">
                    <div class="widget-icon pull-left themed-background-autumn animation-fadeIn">
                        <i class="fa fa-file-text"></i>
                    </div>
                    <h3 class="widget-content text-right animation-pullDown">
                        New <strong>Article</strong><br>
                        <small>Mountain Trip</small>
                    </h3>
                </div>
            </a>
            <!-- END Widget -->
        </div>
        <div class="col-sm-6 col-lg-3">
            <!-- Widget -->
            <a href="page_comp_charts.php" class="widget widget-hover-effect1">
                <div class="widget-simple">
                    <div class="widget-icon pull-left themed-background-spring animation-fadeIn">
                        <i class="gi gi-usd"></i>
                    </div>
                    <h3 class="widget-content text-right animation-pullDown">
                        + <strong>250%</strong><br>
                        <small>Sales Today</small>
                    </h3>
                </div>
            </a>
            <!-- END Widget -->
        </div>
        <div class="col-sm-6 col-lg-3">
            <!-- Widget -->
            <a href="page_ready_inbox.php" class="widget widget-hover-effect1">
                <div class="widget-simple">
                    <div class="widget-icon pull-left themed-background-fire animation-fadeIn">
                        <i class="gi gi-envelope"></i>
                    </div>
                    <h3 class="widget-content text-right animation-pullDown">
                        5 <strong>Messages</strong>
                        <small>Support Center</small>
                    </h3>
                </div>
            </a>
            <!-- END Widget -->
        </div>
        <div class="col-sm-6 col-lg-3">
            <!-- Widget -->
            <a href="page_comp_gallery.php" class="widget widget-hover-effect1">
                <div class="widget-simple">
                    <div class="widget-icon pull-left themed-background-amethyst animation-fadeIn">
                        <i class="gi gi-picture"></i>
                    </div>
                    <h3 class="widget-content text-right animation-pullDown">
                        +30 <strong>Photos</strong>
                        <small>Gallery</small>
                    </h3>
                </div>
            </a>
            <!-- END Widget -->
        </div>
         
    </div>
    
    <div class="row">
        <div class="col-md-12">
            <div class="block">
                <!-- Block Title -->
                <div class="block-title">
                    <h2><strong>Personas</strong> Registradas</h2>
                </div>
                <!-- END Block Title -->

                <!-- Block Content -->
                <div id="cargar_tabla">
                    
                </div>
                <!-- END Block Content -->
            </div>
        </div>
    </div>
    <!-- END Mini Top Stats Row -->
 
</div>



<!-- END Page Content -->

<%@ include file="inc/page_footer.jsp" %> 
<%@ include file="inc/template_scripts.jsp" %>  

<!-- Google Maps API Key (you will have to obtain a Google Maps API key to use Google Maps) -->
<!-- For more info please have a look at https://developers.google.com/maps/documentation/javascript/get-api-key#key -->
<script src="https://maps.googleapis.com/maps/api/js?key="></script>
<script src="js/helpers/gmaps.min.js"></script>

<!-- Load and execute javascript code used only in this page -->
<script src="js/pages/index.js"></script>
<script>$(function(){ Index.init(); });</script>

 
<script>
    
 
    $(function(){//funcion anonima
        
        console.log("Jquery funcionando podemos ver"); 
        $("#cargar_tabla").empty().append("<p>Hola como estas</p>");
        cargar_datos();
        
        
    });
    
    
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
</script>

<%@ include file="inc/template_end.jsp" %> 

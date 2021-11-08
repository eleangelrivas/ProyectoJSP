<%-- 
    Document   : index
    Created on : 08-nov-2021, 7:40:43
    Author     : elenilsonangel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../inc/template_start.jsp" %>
<%@ include file="../../inc/page_head.jsp" %> 

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
            <a href="javascript:void(0)" id="mostrar_modal" data-tipo="registro" class="widget widget-hover-effect1">
                <div class="widget-simple">
                    <div class="widget-icon pull-left themed-background-amethyst animation-fadeIn">
                        <i class="gi gi-picture"></i>
                    </div>
                    <h3 class="widget-content text-right animation-pullDown">
                         <strong>Registro</strong>
                        <small>Personas</small>
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
                    <h2><strong>Salario por</strong> Departamento</h2>
                </div>
                <!-- END Block Title -->

                <!-- Block Content -->
                <div id="cargar_grafico">
                    
                </div>
                <!-- END Block Content -->
                  <!-- Block Content -->
                <div id="cargar_tabla">
                    
                </div>
                <!-- END Block Content -->
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="block">
                <!-- Block Title -->
                <div class="block-title">
                    <h2><strong>Salarios mayore al</strong> Promedio</h2>
                </div>
                <!-- END Block Title -->

                <!-- Block Content -->
                <div id="cargar_grafico2">
                    
                </div>
                <!-- END Block Content -->
                  <!-- Block Content -->
                <div id="cargar_tabla2">
                    
                </div>
                <!-- END Block Content -->
            </div>
        </div>
    </div>
    <!-- END Mini Top Stats Row -->
 
    
    

</div>



<!-- END Page Content -->

<%@ include file="../../inc/page_footer.jsp" %> 
<%@ include file="../../inc/template_scripts.jsp" %>

<script>
    
    $(function(){
        console.log("Jquery funcionando");
        cargar_datos();
        
    })
    
    function cargar_datos(){
        mostrar_cargando_global("Creando Graficos","Por favor no recargue la página");
        var datos = {"consultar_datos":"grafico_depto"};
        $.ajax({
            dataType: "json",
            method: "POST",
            url:'Home',
            data : datos,
        }).done(function(json) {
                console.log("Datos grafico consultado",json);
                if(json[0]['proceso']=="Exito"){
                    $("#cargar_tabla").empty().html(json[0]['la_tabla_html']);//dibuja tabla
                    $("#cargar_tabla2").empty().html(json[0]['tabla_promedio']);//dibujan tabla
                    //dibuja graficos y manda parametros 
                    //id_tabla,contenedor donde se creara grafico,titulo del grafico
                    crear_grafico(json[0]['id_tabla1'],"cargar_grafico","Grafico de salarios por departamento","column");
                    crear_grafico(json[0]['id_tabla2'],"cargar_grafico2","Gráfico de salarios mayores al promedio","pie");
                }
        }).fail(function(){

        }).always(function(){
            Swal.close();
        });
    
        
    }
    
    
    
    function crear_grafico(id_tabla,div_contenedor,titulo,tipo_grafico="column"){
        Highcharts.chart(div_contenedor, {
            data: {
                table: id_tabla
            },
            chart: {
                type: tipo_grafico
            },
            title: {
                text: titulo
            },
            yAxis: {
                allowDecimals: false,
                title: {
                    text: '$'
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        this.point.y + ' ' + this.point.name.toLowerCase();
                }
            }
        });
         
    }
</script>



<%@ include file="../../inc/template_end.jsp" %> 


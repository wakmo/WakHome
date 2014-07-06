<%@ page contentType="text/html"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<!--   taglib uri="http://java.sun.com/jsf/facelets" prefix="ui"%>
 taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
 taglib uri="http://richfaces.org/rich" prefix="rich"%>-->
<f:view>
	<html>
<!--    <ui:composition template="/templates/masterLayout.xhtml">
<ui:define name="content">
<rich:panel id="panelLogin"> -->


<h:form id="user">
	<h:outputText value="Dites quelque chose"></h:outputText>
	<h:panelGrid border="1" columns="2" style="width: 228px; ">
		<h:inputText id="chaine" value="#{valuesTestBean.val.stringVal}">
		</h:inputText>
		<h:commandButton id="entrer" value="entrer"
			action="#{valuesTestBean.go}"></h:commandButton>
	</h:panelGrid>
</h:form>
<!-- 	<rich:toolTip>
					<span style="white-space: nowrap"> Pour etre valide, votre pseudo ne doit comporter au minimum 4 lettres et sans caracteres speciaux. <br/>
					Votre mot de passe doit aussi faire 4 lettres.<br/>
					Un message d'errur sera affiche le cas echeant.
					</span>
				</rich:toolTip>
	</rich:panel>
	</ui:define>
</ui:composition>
 -->
	</html>
</f:view>

<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="wctx" value="${ctx}${applicationScope.webSourcePathKey}" />
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires",0);%> 
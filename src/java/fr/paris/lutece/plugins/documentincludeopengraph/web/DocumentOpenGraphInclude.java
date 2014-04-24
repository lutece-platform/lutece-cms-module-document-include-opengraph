/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.documentincludeopengraph.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.document.business.Document;
import fr.paris.lutece.plugins.document.business.DocumentHome;
import fr.paris.lutece.portal.service.content.PageData;
import fr.paris.lutece.portal.service.includes.PageInclude;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;


/**
 * This class includes metas into the head of the HTML page
 */
public class DocumentOpenGraphInclude implements PageInclude
{
    private static final String DOC_ID = "document_id";
    private static final String DOC = "doc";
    private static final String URL = "url";
    private static final String DOC_OG = "doc_og";
    private static final String _OG_H = "skin/plugins/documentopengraph/document_opengraph_header.html";

    /**
    * Substitue specific Freemarker markers in the page template.
    * @param rootModel the HashMap containing markers to substitute
    * @param data A PageData object containing applications data
    * @param nMode The current mode
    * @param request The HTTP request
    */
    public void fillTemplate( Map<String, Object> rootModel, PageData data, int nMode, HttpServletRequest request )
    {
        try
        {
            String strUrl = request.getRequestURL(  ).toString(  );

            int nId = Integer.parseInt( request.getParameter( DOC_ID ) );
            Document doc = DocumentHome.findByPrimaryKey( nId );
            int nPorletId = Integer.parseInt( request.getParameter( "portlet_id" ) );
            Map<String, Object> model = new HashMap<String, Object>(  );
            model.put( URL, strUrl );
            model.put( DOC, doc );
            model.put( "portletId", nPorletId );

            HtmlTemplate template = AppTemplateService.getTemplate( _OG_H, request.getLocale(  ), model );
            rootModel.put( DOC_OG, template.getHtml(  ) );
        }
        catch ( Exception e )
        {
            //if error, no header include
            rootModel.put( DOC_OG, "" );
        }
    }
}

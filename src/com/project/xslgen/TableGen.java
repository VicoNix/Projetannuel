package com.project.xslgen;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TableGen {

	private int border;

    private ArrayList<Node> colfield = new ArrayList<Node>();

    private NodeList linefield;

    private Node itfield;

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public ArrayList<Node> getColfield() {
		return colfield;
	}

	public void setColfield(ArrayList<Node> nodeList) {
		this.colfield = nodeList;
	}

	public NodeList getLinefield() {
		return linefield;
	}

	public void setLinefield(NodeList nodeList) {
		this.linefield = nodeList;
	}

	public Node getItfield() {
		return itfield;
	}

	public void setItfield(Node itfield) {
		this.itfield = itfield;
	}

    
    /*
    <table border="1">
    <tr bgcolor="#9acd32">
      <th>Title</th>
      <th>Artist</th>
    </tr>
    <xsl:for-each select="catalog/cd">
    <tr>
      <td><xsl:value-of select="title"/></td>
      <td><xsl:value-of select="artist"/></td>
    </tr>
    </xsl:for-each>
  </table>
  */

	public String CreateTable(){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        StringWriter writer = null;
		try {
			builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element table = doc.createElement("table");
        table.setAttribute("border", Integer.toString(border));
        doc.appendChild(table);
        Element tr = doc.createElement("tr");
        tr.setAttribute("bgcolor", "#9acd32");
        table.appendChild(tr);
        Element xsl = doc.createElement("xsl:for-each");
        xsl.setAttribute("select",getFullXPath(itfield));
        table.appendChild(xsl);
        Element tr2 = doc.createElement("tr");
        xsl.appendChild(tr2);
        for(int i=0;i<linefield.getLength();i++)
        {
        	Element td = doc.createElement("td");
        	tr2.appendChild(td);
        	Element xslvalue = doc.createElement("xsl:value-of");
        	xslvalue.setAttribute("select", linefield.item(i).getNodeName());
        	td.appendChild(xslvalue);
        }

        DOMSource domSource = new DOMSource(doc);
        writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        
		} catch (ParserConfigurationException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		return writer.toString();
		
	}
	
	public static String getFullXPath(Node n) {
		// abort early
		if (null == n)
		  return null;

		// declarations
		Node parent = null;
		Stack<Node> hierarchy = new Stack<Node>();
		StringBuffer buffer = new StringBuffer();

		// push element on stack
		hierarchy.push(n);

		switch (n.getNodeType()) {
		case Node.ATTRIBUTE_NODE:
		  parent = ((Attr) n).getOwnerElement();
		  break;
		case Node.ELEMENT_NODE:
		  parent = n.getParentNode();
		  break;
		case Node.DOCUMENT_NODE:
		  parent = n.getParentNode();
		  break;
		default:
		  throw new IllegalStateException("Unexpected Node type" + n.getNodeType());
		}

		while (null != parent && parent.getNodeType() != Node.DOCUMENT_NODE) {
		  // push on stack
		  hierarchy.push(parent);

		  // get parent of parent
		  parent = parent.getParentNode();
		}

		// construct xpath
		Object obj = null;
		while (!hierarchy.isEmpty() && null != (obj = hierarchy.pop())) {
		  Node node = (Node) obj;
		  boolean handled = false;

		  if (node.getNodeType() == Node.ELEMENT_NODE) {
		    Element e = (Element) node;

		    // is this the root element?
		    if (buffer.length() == 0) {
		      // root element - simply append element name
		      buffer.append(node.getNodeName());
		    } else {
		      // child element - append slash and element name
		      buffer.append("/");
		      buffer.append(node.getNodeName());

		      if (node.hasAttributes()) {
		        // see if the element has a name or id attribute
		        if (e.hasAttribute("id")) {
		          // id attribute found - use that
		          buffer.append("[@id='" + e.getAttribute("id") + "']");
		          handled = true;
		        } else if (e.hasAttribute("name")) {
		          // name attribute found - use that
		          buffer.append("[@name='" + e.getAttribute("name") + "']");
		          handled = true;
		        }
		      }

		      if (!handled) {
		        // no known attribute we could use - get sibling index
		        int prev_siblings = 1;
		        Node prev_sibling = node.getPreviousSibling();
		        while (null != prev_sibling) {
		          if (prev_sibling.getNodeType() == node.getNodeType()) {
		            if (prev_sibling.getNodeName().equalsIgnoreCase(
		                node.getNodeName())) {
		              prev_siblings++;
		            }
		          }
		          prev_sibling = prev_sibling.getPreviousSibling();
		        }
		        buffer.append("[" + prev_siblings + "]");
		      }
		    }
		  } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
		    buffer.append("/@");
		    buffer.append(node.getNodeName());
		  }
		}
		// return buffer
		return buffer.toString();
		}

      

}



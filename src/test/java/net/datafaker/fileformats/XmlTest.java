package net.datafaker.fileformats;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlTest {
    @ParameterizedTest
    @MethodSource("generateTestXmlPretty")
    public void xmlPrettyTest(Xml.XmlNode xmlNode, String expected) {
        Xml xml = new Xml(xmlNode);
        assertEquals(expected, xml.generate(true));
    }

    private static Stream<Arguments> generateTestXmlPretty() {
        return Stream.of(
            Arguments.of(new Xml.XmlNode("root", Collections.emptyList()), "<root/>"),
            Arguments.of(new Xml.XmlNode("root", "value"), "<root>value</root>"),
            Arguments.of(new Xml.XmlNode("root",
                    map(entry("attribute1", "value1"), entry("attribute2", "value2")), "value"),
                "<root attribute1=\"value1\" attribute2=\"value2\">value</root>"),
            Arguments.of(new Xml.XmlNode("root",
                    map(entry("attribute1", "value1"), entry("attribute2", "value2")),
                    Collections.singletonList(new Xml.XmlNode("child", "value"))),
                "<root attribute1=\"value1\" attribute2=\"value2\">" + System.lineSeparator() + "    <child>value</child>" + System.lineSeparator() + "</root>"),
            Arguments.of(new Xml.XmlNode("root", "<> value\""), "<root>&lt;&gt; value&quot;</root>")
        );
    }

    @ParameterizedTest
    @MethodSource("generateTestXml")
    public void xmlTest(Xml.XmlNode xmlNode, String expected) {
        Xml xml = new Xml(xmlNode);
        assertEquals(expected, xml.generate());
    }

    private static Stream<Arguments> generateTestXml() {
        return Stream.of(
            Arguments.of(new Xml.XmlNode("root", Collections.emptyList()), "<root/>"),
            Arguments.of(new Xml.XmlNode("root", "value"), "<root>value</root>"),
            Arguments.of(new Xml.XmlNode("root",
                    map(entry("attribute1", "value1"), entry("attribute2", "value2")), "value"),
                "<root attribute1=\"value1\" attribute2=\"value2\">value</root>"),
            Arguments.of(new Xml.XmlNode("root",
                    map(entry("attribute1", "value1"), entry("attribute2", "value2")),
                    Collections.singletonList(new Xml.XmlNode("child", "value"))),
                "<root attribute1=\"value1\" attribute2=\"value2\"><child>value</child></root>"),
            Arguments.of(new Xml.XmlNode("root", "<> value\""), "<root>&lt;&gt; value&quot;</root>")
        );
    }

    private static Map.Entry<String, String> entry(String key, String value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private static Map<String, String> map(Map.Entry<String, String>... entries) {
        Map<String, String> map = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}

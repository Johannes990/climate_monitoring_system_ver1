import xml.etree.ElementTree as ElementTree
import random


def generate_random_xml(device_id: str, device_type: str):
    envelope = ElementTree.Element("soap:Envelope",
                                   attrib={
                                    "xmlns:soap": "http://schemas.xmlsoap.org/soap/envelope/",
                                    "xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
                                    "xmlns:xsd": "http://www.w3.org/2001/XMLSchema"
                                 }
                                   )

    body = ElementTree.SubElement(envelope, "soap:Body")

    insert_sample = ElementTree.SubElement(body, "InsertTx5xxSample", attrib={
        "xmlns": "http://cometsystem.cz/schemas/soapTx5xx_v2.xsd"
    })

    # add some random data here into our document
    pass_key = device_id
    device = device_type
    temp = round(random.uniform(15, 36), 1)
    rel_hum = round(random.uniform(25, 60), 1)
    comp_quant = round(random.uniform(0, 10), 1)
    pressure = -9999
    alarms = ",".join(random.choice(["yes", "no"]) for _ in range(4))
    comp_type = "Dew point"
    temp_u = "C"
    pressure_u = "n/a"
    timer = "10"

    ElementTree.SubElement(insert_sample, "passKey").text = pass_key
    ElementTree.SubElement(insert_sample, "device").text = device
    ElementTree.SubElement(insert_sample, "temp").text = str(temp)
    ElementTree.SubElement(insert_sample, "relHum").text = str(rel_hum)
    ElementTree.SubElement(insert_sample, "compQuant").text = str(comp_quant)
    ElementTree.SubElement(insert_sample, "pressure").text = str(pressure)
    ElementTree.SubElement(insert_sample, "alarms").text = alarms
    ElementTree.SubElement(insert_sample, "compType").text = comp_type
    ElementTree.SubElement(insert_sample, "tempU").text = temp_u
    ElementTree.SubElement(insert_sample, "pressureU").text = pressure_u
    ElementTree.SubElement(insert_sample, "timer").text = timer

    tree = ElementTree.ElementTree(envelope)
    return tree


if __name__ == '__main__':
    device_id = "17962108"
    device_type = "3510"
    n = 5
    for i in range(n):
        random_xml = generate_random_xml(device_id, device_type)
        random_xml.write(f"generated_xmls/{device_type}_{device_id}_{i}.xml", encoding="utf-8", xml_declaration=True)

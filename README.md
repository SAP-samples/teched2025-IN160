# IN160 - Empower your business through enterprise automation.

## Description

This repository contains the material for the SAP TechEd 2025 session called IN160 - Empower your business through enterprise automation.

## Overview

This session introduces attendees to [Enterprise Automation](https://www.sap.com/products/technology-platform/enterprise-automation.html) and how SAP Integration Suite, SAP Build, and SAP Signavio work together to optimize business processes. 
Enterprise Automation is a comprehensive method for improving business processes through the smart integration of technologies such as Robotic Process Automation (RPA), Artificial Intelligence (AI), and cloud-based platforms. It goes beyond automating individual tasks and focuses on transforming entire end-to-end processes. The goal is to increase efficiency, accuracy, and agility by enabling fully integrated interaction across systems and departments.

## The scenario

You'll learn how to automate order processing for SAP and third-party applications. For that, you will subscribe to order creation events in Shopify and set up your iFlow to post and replicate orders in both SAP S/4HANA and Shopify. 
Following, you will move on to creating a custom approval process in SAP Build. The goal of this process is to add validation and configuration steps, which will be managed through conditions and human-based approval tasks. 
The process in SAP Build will be triggered by the order event configured in the Event Mesh service. Fortunately, Event Mesh and SAP Build integrate natively. This same native integration applies to iFlows created in SAP Integration Suite. This connection is important because, once an order has been approved, the iFlow will be triggered to post and replicate the order in both SAP S/4HANA and Shopify. 

<br>![](/exercises/ex0/images/Scenario.png)

Beyond business applications and workspaces, SAP Build also enables you to create custom process extensions, RPA bots, and automations that help free employees from repetitive tasks. As you will see in this workshop, SAP Build offers an intuitive Low-Code/No-Code environment, allowing you to build extensions quickly using simple drag-and-drop functionality.

## Exercises

Provide the exercise content here directly in README.md using [markdown](https://guides.github.com/features/mastering-markdown/) and linking to the specific exercise pages, below is an example.

- [Exercise 0 - Get ready](exercises/ex0/)
- [Exercise 1 - Configure Event Mesh Queues in SAP Integration Suite](../ex1/README.md)
- [Exercise 2 - Replicate approved sales order to Shopify and SAP S/4HANA](../ex3/README.md)
- [Excercise 3 - Build a custom approval process in SAP Build](../ex4/README.md)


**IMPORTANT**

Your repo must contain the .reuse and LICENSES folder and the License section below. DO NOT REMOVE the section or folders/files. Also, remove all unused template assets(images, folders, etc) from the exercises folder. 

## Contributing
Please read the [CONTRIBUTING.md](./CONTRIBUTING.md) to understand the contribution guidelines.

## Code of Conduct
Please read the [SAP Open Source Code of Conduct](https://github.com/SAP-samples/.github/blob/main/CODE_OF_CONDUCT.md).

## How to obtain support

Support for the content in this repository is available during the actual time of the online session for which this content has been designed. Otherwise, you may request support via the [Issues](../../issues) tab.

## License
Copyright (c) 2024 SAP SE or an SAP affiliate company. All rights reserved. This project is licensed under the Apache Software License, version 2.0 except as noted otherwise in the [LICENSE](LICENSES/Apache-2.0.txt) file.

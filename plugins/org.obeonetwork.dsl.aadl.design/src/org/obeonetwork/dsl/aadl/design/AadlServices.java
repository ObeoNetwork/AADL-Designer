package org.obeonetwork.dsl.aadl.design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.DirectionType;
import org.osate.aadl2.ProcessImplementation;
import org.osate.aadl2.ProcessType;
import org.osate.aadl2.SystemImplementation;
import org.osate.aadl2.ThreadImplementation;

public class AadlServices {

	public ArrayList<AadlPackage> getPackages(EObject model) {

		/*
		 * public List<AadlPackage> getPackages(EObject model){ Get all the
		 * resources of the resource set // List<Resource> aadlFiles =
		 * resSet.getResources(); // Clean the aadlFiles to get only the aadl
		 * files // For each resource get the aadl package element // Add it to
		 * a result list // Return the aadl packages
		 */
		System.out
				.println("\n============================= Starting AADLServices ===========================");
		// Getting the resource set
		ResourceSet resSet = model.eResource().getResourceSet();
		ArrayList<AadlPackage> aadlPackageList = new ArrayList<AadlPackage>();

		// getting all the resources of the resource set
		List<org.eclipse.emf.ecore.resource.Resource> aadlFiles = resSet
				.getResources();

		for (org.eclipse.emf.ecore.resource.Resource r : aadlFiles) {
			// converting resource to string
			String resString = r.toString();

			if (resString.substring(resString.length() - 5,
					resString.length() - 1).equals("aadl")) {
				// found the correct aadl resource
				TreeIterator<EObject> i = r.getAllContents();
				while (i.hasNext()) {
					EObject checkingResourceClassName = i.next();
					// checking if class is AadlPackage
					if (checkingResourceClassName.eClass().getName()
							.equals("AadlPackage")) {
						// printing the name
						System.out.println(checkingResourceClassName.eClass()
								.getName());
						aadlPackageList
								.add((AadlPackage) checkingResourceClassName);

					}
				}

			}
		}

		// System.out.println(aadlFiles);

		System.out.println("Returned list:" + aadlPackageList.toString());
		System.out
				.println("============================= Ending AADLServices ===========================\n");
		return aadlPackageList;
	}

	public ArrayList<ComponentType> getComponents(EObject model, String componentType){
		ArrayList<ComponentType> componentsCollectionResult = new ArrayList<ComponentType>();
	

		//getting the iterator for all the objects
		TreeIterator<EObject> i = model.eAllContents();
		
		//creating the Resource list to be returned
		while (i.hasNext()){
			EObject singleResource = i.next();
			
			//finding the desired component Type
			if (singleResource.eClass().getName().equals(componentType)){
		
				//we have a component to add to the collection
				componentsCollectionResult.add((ComponentType)singleResource);
			}
		}
		
		return componentsCollectionResult;
	}

	public ArrayList<ComponentType> getPortComponents(EObject model, String componentType){
		ArrayList<ComponentType> componentsCollectionResult = new ArrayList<ComponentType>();
	

		//getting the iterator for all the objects
		TreeIterator<EObject> i = model.eContainer().eAllContents();
		
		//creating the Resource list to be returned
		while (i.hasNext()){
			EObject singleResource = i.next();
			
			//finding the desired component Type
			if (singleResource.eClass().getName().equals(componentType)){
		
				//we have a component to add to the collection
				componentsCollectionResult.add((ComponentType)singleResource);
			}
		}
		
		return componentsCollectionResult;
	}
	
	public ArrayList<DataPort> getDirectedPorts(EObject model, String portDirection) {
		ArrayList<DataPort> directedPorts = new ArrayList<DataPort>();
		
		TreeIterator<EObject> i = model.eAllContents();
		while (i.hasNext()) {
			EObject singleResource = i.next();

			// finding the desired component Type
			if (singleResource instanceof DataPort) {
				DataPort port = (DataPort) singleResource;
				DirectionType direction = port.getDirection();
				//System.out.println(port.getDirection());
				if (direction.incoming() && !direction.outgoing() && portDirection.equals("input")){
					//System.out.println("Port : " + port.getName()	+ " is an input port");
					directedPorts.add(port);
				}
				if (direction.outgoing() && !direction.incoming() && portDirection.equals("output")){
					//System.out.println("Port : " + port.getName()	+ " is an output port");
					directedPorts.add(port);
				}
				if (direction.incoming() && direction.outgoing() && portDirection.equals("inout")){
					//System.out.println("Port : " + port.getName()	+ " is an input/output port");
					directedPorts.add(port);
				}
			}
		}
		return directedPorts;
	}
	
	public ArrayList<DataPort> getProcessImplementationDirectedPorts(EObject model, String portDirection) {
		ArrayList<DataPort> directedPorts = new ArrayList<DataPort>();
		
		if (model instanceof ProcessImplementation){
			//System.out.println("We have the right type and can move on!");
			ProcessImplementation processImpl = (ProcessImplementation) model;
			//System.out.println("membri: "+ processImpl.getInheritedMembers());
			
			
			for (EObject i : processImpl.getInheritedMembers()){
				if (i instanceof DataPort){
					DataPort port = (DataPort) i;
					//System.out.println("Port name: " + port.getName() + "\n");
					
					DirectionType direction = port.getDirection();
					//System.out.println(port.getDirection());
					if (direction.incoming() && !direction.outgoing() && portDirection.equals("input")){
						//System.out.println("Port : " + port.getName()	+ " is an input port");
						directedPorts.add(port);
					}
					if (direction.outgoing() && !direction.incoming() && portDirection.equals("output")){
						//System.out.println("Port : " + port.getName()	+ " is an output port");
						directedPorts.add(port);
					}
					if (direction.incoming() && direction.outgoing() && portDirection.equals("inout")){
						//System.out.println("Port : " + port.getName()	+ " is an input/output port");
						directedPorts.add(port);
					}
				}
			}
		}
			
		return directedPorts;
	}
	
	
	public ArrayList<DataPort> getThreadImplementationDirectedPorts(EObject model, String portDirection) {
		ArrayList<DataPort> directedPorts = new ArrayList<DataPort>();
		
		if (model instanceof ThreadImplementation){
			//System.out.println("We have the right type and can move on!");
			ThreadImplementation threadImpl = (ThreadImplementation) model;
			//System.out.println("membri: "+ processImpl.getInheritedMembers());
			
			
			for (EObject i : threadImpl.getInheritedMembers()){
				if (i instanceof DataPort){
					DataPort port = (DataPort) i;
					//System.out.println("Port name: " + port.getName() + "\n");
					
					DirectionType direction = port.getDirection();
					//System.out.println(port.getDirection());
					if (direction.incoming() && !direction.outgoing() && portDirection.equals("input")){
						//System.out.println("Port : " + port.getName()	+ " is an input port");
						directedPorts.add(port);
					}
					if (direction.outgoing() && !direction.incoming() && portDirection.equals("output")){
						//System.out.println("Port : " + port.getName()	+ " is an output port");
						directedPorts.add(port);
					}
					if (direction.incoming() && direction.outgoing() && portDirection.equals("inout")){
						//System.out.println("Port : " + port.getName()	+ " is an input/output port");
						directedPorts.add(port);
					}
				}
			}
		}
			
		return directedPorts;
	}
	
	public ArrayList<DataPort> getSystemImplementationDirectedPorts(EObject model, String portDirection) {
		ArrayList<DataPort> directedPorts = new ArrayList<DataPort>();
		
		if (model instanceof SystemImplementation){
			//System.out.println("We have the right type and can move on!");
			SystemImplementation systemImpl = (SystemImplementation) model;
			//System.out.println("membri: "+ processImpl.getInheritedMembers());
			
			
			for (EObject i : systemImpl.getInheritedMembers()){
				if (i instanceof DataPort){
					DataPort port = (DataPort) i;
					//System.out.println("Port name: " + port.getName() + "\n");
					
					DirectionType direction = port.getDirection();
					//System.out.println(port.getDirection());
					if (direction.incoming() && !direction.outgoing() && portDirection.equals("input")){
						//System.out.println("Port : " + port.getName()	+ " is an input port");
						directedPorts.add(port);
					}
					if (direction.outgoing() && !direction.incoming() && portDirection.equals("output")){
						//System.out.println("Port : " + port.getName()	+ " is an output port");
						directedPorts.add(port);
					}
					if (direction.incoming() && direction.outgoing() && portDirection.equals("inout")){
						//System.out.println("Port : " + port.getName()	+ " is an input/output port");
						directedPorts.add(port);
					}
				}
			}
		}
			
		return directedPorts;
	}
	
}

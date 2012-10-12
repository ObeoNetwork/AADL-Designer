package org.obeonetwork.dsl.aadl.design;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.osate.aadl2.Aadl2Package;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.DirectionType;
import org.osate.aadl2.SystemType;
import org.osate.aadl2.ThreadType;

public class AadlServices {

	public ArrayList<AadlPackage> getPackages(EObject model) {

		/*
		 * public List<AadlPackage> getPackages(EObject model){
		 * Get all the resources of the resource set 
		 * // List<Resource> aadlFiles = resSet.getResources(); 
		 * // Clean the aadlFiles to get only the aadl files 
		 * // For each resource get the aadl package element 
		 * // Add it to a result list 
		 * // Return the aadl packages
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
					//checking if class is AadlPackage
					if (checkingResourceClassName.eClass().getName().equals("AadlPackage")) {
						//printing the name
						System.out.println(checkingResourceClassName.eClass().getName());
						aadlPackageList.add((AadlPackage)checkingResourceClassName);
						
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
		ArrayList<ComponentType> componentsCollectionResult = new ArrayList<>();
	

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
	
	public void getDirectedPorts(EObject model, String portDirection){
		System.out.println("<<<<<<<<<<<<<<<<<<<==========getDirectedPorts==============>>>>>>>>>>>>>>>>>>>>>\n");
		//System.out.println(model.eContents());
		TreeIterator<EObject> i = model.eAllContents();
		DirectionType dt;
		while (i.hasNext()){
			EObject singleResource = i.next();
			
			//finding the desired component Type
			if (singleResource.eClass().getName().equals("DataPort")){
		
				//System.out.println("Attributes: " + singleResource.eClass().getEAllAttributes().get(2));
				System.out.println(singleResource);
			}
		}
		
		System.out.println("\n<<<<<<<<<<<<<<<<<<<=========getDirectedPorts===============>>>>>>>>>>>>>>>>>>>>>\n");
	}
	
	
}

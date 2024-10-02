//
//  AppDelegate.swift
//  iosApp
//
//  Created by Irwan Hermawan on 30/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import ComposeApp

class AppDelegate : NSObject, UIApplicationDelegate {
    
    private let appDelegateAdapter = AppDelegateAdapter()
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        return appDelegateAdapter.application(application: application, didFinishLaunchingWithOptions: launchOptions)
    }
}

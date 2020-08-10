require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "IrisLibrary"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-iris-library
                   DESC
  s.homepage     = "https://github.com/github_account/react-native-iris-library"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "Thiago Brandt" => "thiago.brandt@somosiris.com" }
  s.platforms    = { :ios => "10.0" }
  s.source       = { :git => "https://github.com/github_account/react-native-iris-library.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true
  s.swift_version = "5.0"

  s.dependency "React"
  # ...
  s.dependency "IrisSDKStatic", ">=0.9.0"
  s.dependency "OneSignal"
end


package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"os/exec"
	"strings"
)

type image struct {
	idx  int
	name string
	mask bool
}

func newMaskedImage(idx int, name string) image {
	return image{idx, name, true}
}

func newSimpleImage(idx int, name string) image {
	return image{idx, name, false}
}

var images = []image{
	newMaskedImage(0, "jmc-logo"),
	newMaskedImage(2, "jmc-welcome-screen"),
	newMaskedImage(4, "jmc-welcome-screen-close"),
	newMaskedImage(6, "jmc-start-screen"),
	newMaskedImage(8, "eclipse-java-perspective"),
	newMaskedImage(10, "eclipse-preferences-installed-jres"),
	newMaskedImage(12, "eclipse-preferences-execution-environments"),
	newMaskedImage(14, "eclipse-import-existing-projects"),
	newMaskedImage(16, "eclipse-import-tutorial-all-projects"),
	newMaskedImage(18, "eclipse-workspace-projects-loaded"),
	newMaskedImage(20, "eclipse-jmc-perspective-button-toggle"),
	newMaskedImage(22, "eclipse-jmc-perspective-jvm-browser"),
	newMaskedImage(24, "eclipse-jmc-launch-wizard-jfr"),
	newSimpleImage(26, "eclipse-java-perspective-button"),
	newMaskedImage(27, "eclipse-java-perspective-button-toggle"),
	newMaskedImage(29, "eclipse-run-do-nothing-program"),
	newMaskedImage(31, "eclipse-jmc-start-flight-recording"),
	newMaskedImage(33, "eclipse-jmc-jvm-browser-flight-recorder-node-expanded"),
	newMaskedImage(35, "eclipse-jmc-automated-recording-analysis-do-nothing"),
	newMaskedImage(37, "eclipse-jmc-application-outline-view-do-nothing"),
	newMaskedImage(39, "eclipse-java-perspective-console-view"),
	newMaskedImage(41, "eclipse-debug-perspective-terminate"),
	newMaskedImage(43, "eclipse-close-recording-window"),
	newMaskedImage(45, "eclipse-jmc-automated-recording-analysis-hotmethods"),
	newSimpleImage(47, "eclipse-jmc-application-outline-view-hotmethods"),
	newSimpleImage(48, "eclipse-jmc-method-profiling-hotmethods"),
	newMaskedImage(49, "branch-icon-0"),
	newMaskedImage(51, "branch-icon-1"),
	newMaskedImage(53, "branch-icon-2"),
	newMaskedImage(55, "branch-icon-3"),
	newSimpleImage(57, "eclipse-jmc-stacktrace-next-frame-group"),
	newMaskedImage(58, "eclipse-jmc-stacktrace-tree"),
	newSimpleImage(60, "eclipse-jmc-stacktrace-line-numbers"),
	newMaskedImage(61, "eclipse-jmc-analysis-offending-monitor"),
	newSimpleImage(63, "eclipse-jmc-lock-instances-page"),
	newSimpleImage(64, "eclipse-jmc-latency-before-view-all-threads"),
	newSimpleImage(65, "eclipse-jmc-latency-fixed"),
	newSimpleImage(66, "eclipse-jmc-latency-fixed-all-threads"),
	newSimpleImage(67, "eclipse-jmc-garbage-collection"),
	newSimpleImage(68, "eclipse-jmc-memory-page"),
	newMaskedImage(69, "eclipse-jmc-jfr-memory-live-objects"),
	newMaskedImage(71, "eclipse-jmc-jfr-jdbc"),
	newSimpleImage(73, "eclipse-jmc-jfr-weblogic-servlet"),
	newSimpleImage(74, "eclipse-jmc-jfr-weblogic-servlet-focused-selection"),
	newMaskedImage(75, "eclipse-jmc-jfr-weblogic-events-matching-selection"),
	newSimpleImage(77, "eclipse-jmc-jfr-weblogic-event-browser"),
	newMaskedImage(78, "javafx-application-lasers"),
	newSimpleImage(80, "eclipse-jmc-jfr-javafx"),
	newSimpleImage(81, "eclipse-jmc-jfr-javafx-pulse"),
	newMaskedImage(82, "eclipse-jmc-jfr-automated-analysis-exceptions"),
	newSimpleImage(84, "eclipse-jmc-jfr-exceptions-page"),
	newSimpleImage(85, "eclipse-jmc-jfr-exceptions-stacktrace"),
	newSimpleImage(86, "eclipse-jmc-jfr-exceptions-selection"),
	newSimpleImage(87, "eclipse-jmc-jfr-exceptions-selection-2"),
	newSimpleImage(88, "eclipse-jmc-jfr-exceptions-selection-zoom-in"),
	newSimpleImage(89, "eclipse-jmc-jfr-exceptions-event-bucket"),
	newSimpleImage(90, "eclipse-jmc-jfr-fibonacci-threads"),
	newSimpleImage(91, "eclipse-jmc-jfr-fibonacci-threads-other-event-types"),
	newSimpleImage(92, "eclipse-plugin-project-wizard"),
	newMaskedImage(93, "eclipse-plugin-project-window"),
	newSimpleImage(95, "eclipse-plugin-project-no-ui-contribution"),
	newSimpleImage(96, "eclipse-plugin-project-simple-jfr-rule"),
	newSimpleImage(97, "eclipse-run-configurations-application-idle"),
	newMaskedImage(98, "eclipse-jmc-launch-with-workspace-plugins"),
	newSimpleImage(100, "eclipse-jmc-jfr-recording-idle"),
	newSimpleImage(101, "eclipse-jmc-custom-rule-triggering"),
	newSimpleImage(102, "eclipse-plugin-project-export-rule"),
	newMaskedImage(103, "eclipse-jmc-custom-pages"),
	newMaskedImage(105, "eclipse-jmc-rename-custom-page"),
	newMaskedImage(107, "eclipse-jmc-custom-page-filter-duration"),
	newMaskedImage(109, "eclipse-jmc-custom-page-filter-duration-threshold"),
	newSimpleImage(111, "eclipse-jmc-custom-page-view"),
	newMaskedImage(112, "eclipse-jmc-request-log-ecid-grouping"),
	newMaskedImage(114, "eclipse-jmc-request-log-remove-type-filter"),
	newMaskedImage(116, "eclipse-jmc-request-log-context-menu-group-by-ecid"),
	newMaskedImage(118, "eclipse-jmc-request-log-add-column"),
	newSimpleImage(120, "eclipse-jmc-request-log-view"),
	newMaskedImage(121, "eclipse-jmc-log4j-contention-show-search"),
	newMaskedImage(123, "eclipse-jmc-log4j-contention-add-filter"),
	newMaskedImage(125, "eclipse-jmc-log4j-contention-combine-filter"),
	newSimpleImage(127, "eclipse-jmc-log4j-contention-bug"),
	newSimpleImage(128, "eclipse-jmc-log4j-contention-add-grouping"),
	newSimpleImage(129, "eclipse-jmc-management-console"),
	newSimpleImage(130, "eclipse-jmc-management-console-blank-chart"),
	newSimpleImage(131, "eclipse-jmc-management-console-thread-count"),
	newSimpleImage(132, "eclipse-jmc-thread-count-freeze-graph"),
	newSimpleImage(133, "eclipse-jmc-mbean-browser"),
	newSimpleImage(134, "eclipse-jmc-reset-to-default-controls"),
	newSimpleImage(135, "eclipse-jmc-thread-graph-deadlocked"),
	newSimpleImage(136, "eclipse-jmc-add-trigger"),
	newSimpleImage(137, "eclipse-jmc-trigger-alerts"),
	newSimpleImage(138, "eclipse-jmc-joverflow"),
	newSimpleImage(139, "arrow"),
	newMaskedImage(140, "eclipse-jmc-referer-tree"),
	newSimpleImage(142, "eclipse-jmc-reset-class-histogram"),
	newSimpleImage(143, "eclipse-jmc-ancestor-referer"),
}

func (i image) altText() string {
	return strings.ReplaceAll(i.name, "-", " ")
}

type config struct {
	srcpdf string
	srcdir string
	dstdir string
	mdfile string
}

func newConfig(root string) config {
	srcpdf := root + "/JMC_Tutorial.pdf"
	_, err := os.Stat(srcpdf)
	check(err)
	var tmpdir, _ = ioutil.TempDir("/tmp", "extracted-images-")
	return config{
		srcpdf: srcpdf,
		srcdir: tmpdir,
		dstdir: root + "/docs/images",
		mdfile: root + "/docs/images.md",
	}
}

func (c config) srcpath(i image) string {
	return fmt.Sprintf("%s/img-%03d.png", c.srcdir, i.idx)
}

func (c config) maskpath(i image) string {
	return fmt.Sprintf("%s/img-%03d.png", c.srcdir, i.idx+1)
}

func (c config) dstpath(i image) string {
	return fmt.Sprintf("%s/%s.png", c.dstdir, i.name)
}

func (c config) markdown(i image) (string, string) {
	var ref = "![" + i.altText() + "][" + i.name + "]"
	var link = "[" + i.name + "]: images/" + i.name + ".png"
	return ref, link
}

func check(err error) {
	if err != nil {
		log.Fatalf("❌ Image processing failed: %v", err)
	}
}

func main() {
	if len(os.Args) != 2 {
		log.Fatal("Usage: go run images.go <jmc tutorial project root>")
	}
	rootdir := os.Args[1]
	c := newConfig(rootdir)
	log.Printf("🤖 extracting and processing images from '%s'", c.srcpdf)

	// brew install poppler
	cmd := exec.Command("pdfimages", "-png", c.srcpdf, c.srcdir+"/img")
	_, err := cmd.CombinedOutput()
	check(err)
	files, _ := ioutil.ReadDir(c.srcdir)
	log.Printf("🧲 %d images and masks extracted in '%s/'", len(files), c.srcdir)

	os.MkdirAll(c.dstdir, 0755)
	for _, img := range images {
		var cmd *exec.Cmd
		if img.mask {
			// brew install imagemagick
			cmd = exec.Command("convert", c.srcpath(img), c.maskpath(img), "-alpha", "off", "-compose", "copy-opacity", "-composite", c.dstpath(img))
		} else {
			cmd = exec.Command("cp", c.srcpath(img), c.dstpath(img))
		}
		stdoutStderr, err := cmd.CombinedOutput()
		if err != nil {
			fmt.Printf("Command failed: %s\n%s\n", cmd, stdoutStderr)
			log.Fatal(err)
		}
	}
	log.Printf("✅ images processed successfully, output in '%s/'", c.dstdir)

	refs, links := []string{}, []string{}
	for _, img := range images {
		ref, link := c.markdown(img)
		refs = append(refs, ref)
		links = append(links, link)
	}
	md := strings.Join(refs, "\n") + "\n\n" + strings.Join(links, "\n")
	ioutil.WriteFile(c.mdfile, []byte(md), 0644)
	log.Printf("📖 links and references written in '%s'", c.mdfile)

	os.RemoveAll(c.srcdir)
	log.Printf("🧹 deleted temporary directory '%s'", c.srcdir)
}

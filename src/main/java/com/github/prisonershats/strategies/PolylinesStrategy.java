package com.github.prisonershats.strategies;

import com.github.prisonershats.PrisonersHatsStrategy;

import java.util.*;

public class PolylinesStrategy implements PrisonersHatsStrategy<Integer> {

    private List<List<Integer>> orthogonalPolylines;

    public Integer guessHat(List<Integer> heardBefore, List<Integer> seenAhead) {
        int n = heardBefore.size() + seenAhead.size() + 1;

        // calcul des polylines orthogonales, 1 seule fois car elles sont uniquement dépendantes de la taille du problème (n)
        if (orthogonalPolylines == null || orthogonalPolylines.get(0).size() != n) { // si n change, recalcul aussi
            orthogonalPolylines = computeADeterministSetOfOrthogonalPolylines(n);
        }

        // comparer la situation vue par le prisonnier avec toutes les polylines contenues dans orthogonalPolylines
        // théoriquement, il devra toujours y avoir une unique polyline qui matche
        // il suffit ensuite de répondre par le nombre à la position du prisonnier courant dans cette polyline qui matche
        int prisonerPos = heardBefore.size();

        List<Integer> heardAndSeen = new ArrayList<>(n);
        for (Integer elem : heardBefore) {
            heardAndSeen.add(elem);
        }
        heardAndSeen.add(-1); // dummy number... not seen
        for (Integer elem : seenAhead) {
            heardAndSeen.add(elem);
        }

        for (List<Integer> polyline : orthogonalPolylines) {
            boolean match = true;
            for (int j = 0; j < polyline.size(); j++) {
                if (j != prisonerPos && !polyline.get(j).equals(heardAndSeen.get(j))) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return polyline.get(prisonerPos);
            }
        }

        // theoretically unreachable
        return -1;
    }

    private List<List<Integer>> computeADeterministSetOfOrthogonalPolylines(int n) {
        Stack<List<Integer>> orthogonalPolylines;

        // generer toutes ((n + 1)!/2) les sous-polylines de taille n-1 parmi les n+1 positions
        List<List<Integer>> allSubPolylines = enumeratePolylines(n - 1, n + 1);
        System.out.println(allSubPolylines.size());
        List<List<Integer>> missingsPositions = new ArrayList<>(allSubPolylines.size());
        for (List<Integer> polyline : allSubPolylines) {
            missingsPositions.add(getOrderedMissingPositions(polyline, n + 1));
            // left pad polylines with '-1' nodes
            polyline.add(0, -1);
        }

        // trouver le premier set orthogonal de polylines, de taille n parmi n+1 positions
        // Depth-first-search non-récursif (car pour n=7, la profondeur est déjà de (n+1!)/2 = 20160)
        orthogonalPolylines = new Stack<>();
        int pos = 0;
        int[] branchAlreadyTested = new int[allSubPolylines.size()];
        while (pos < allSubPolylines.size()) {
            System.out.println(pos);
            List<Integer> currentPolyline = allSubPolylines.get(pos);
            if (branchAlreadyTested[pos] < 2) {
                currentPolyline.set(0, missingsPositions.get(pos).get(branchAlreadyTested[pos]));
                branchAlreadyTested[pos]++;
                orthogonalPolylines.push(currentPolyline);
                if (isOrthogonal(orthogonalPolylines)) {
                    pos++;
                } else {
                    orthogonalPolylines.pop();
                }
            }
            else { // branchAlreadyTested[pos] == 2
                branchAlreadyTested[pos] = 0;
                orthogonalPolylines.pop();
                pos--;
            }
        }
        return orthogonalPolylines;
    }

    private List<List<Integer>> enumeratePolylines(int nodesCount, int positionsCount) {
        List<List<Integer>> polylines = new ArrayList<>();
        if (nodesCount == 1) {
            for (int i = 0; i < positionsCount; i++) {
                List<Integer> polyline = new ArrayList<>();
                polyline.add(i);
                polylines.add(polyline);
            }
        }
        else {
            // nodesCount > 1
            for (List<Integer> subPolyline : enumeratePolylines(nodesCount - 1, positionsCount)) { // max recursion depth : n
                for (Integer missingPosition : getOrderedMissingPositions(subPolyline, positionsCount)) {
                    List<Integer> polyline = new ArrayList<>(subPolyline);
                    polyline.add(0, missingPosition);
                    polylines.add(polyline);
                }
            }
        }
        return polylines;
    }

    // à fusionner avec enumeratePolylines ?
    private List<Integer> getOrderedMissingPositions(List<Integer> polyline, int positionsCount) {
        List<Integer> missingPositions = new ArrayList<>();
        for (int i = 0; i < positionsCount; i++) {
            if (!polyline.contains(i)) {
                missingPositions.add(i);
            }
        }
        return missingPositions;
    }

    // IDEE D'OPTIM pour isOrtho : ne checker les clashs qu'avec la polyline nouvellement ajoutée ?
    private boolean isOrthogonal(List<List<Integer>> polylines) {
        // pour chaque prisonnier fictif, vérifier que les toutes les sous-polylines sont uniques
        for (int j = 0; j < polylines.get(0).size(); j++) {
            Set<List<Integer>> subPolylinesHasher = new HashSet<>();
            for (List<Integer> polyline : polylines) {
                List<Integer> subPolyline = new LinkedList<>(polyline);
                subPolyline.remove((int) j); // casting needed !
                if (subPolylinesHasher.contains(subPolyline)) {
                    return false;
                }
                else {
                    subPolylinesHasher.add(subPolyline);
                }
            }
        }
        return true;
    }

}